package haffner.io.server.service;

import haffner.io.server.controller.ChatbotMessageController;
import haffner.io.server.data.domain.ChatbotMessage;
import haffner.io.server.data.domain.ChatbotStats;
import haffner.io.server.data.dto.ChatbotExchangeDTO;
import haffner.io.server.repository.ChatbotMessageRepository;
import haffner.io.server.repository.ChatbotStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ChatbotMessageService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotMessageController.class);


    @Value("${chatbot.url}")
    private String chatbotUrl;

    private final ChatbotMessageRepository messageRepository;
    private final ChatbotStatsRepository statsRepository;
    private final RestTemplate restTemplate;

    public ChatbotMessageService(ChatbotMessageRepository messageRepository, ChatbotStatsRepository statsRepository, RestTemplate restTemplate) {
        this.messageRepository = messageRepository;
        this.statsRepository = statsRepository;
        this.restTemplate = restTemplate;
    }

    public List<ChatbotMessage> findAllByConversationId(String conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    public ChatbotExchangeDTO askThenStoreData(ChatbotExchangeDTO dtoRequest) {
        ChatbotMessage userMessage = messageBuilder(dtoRequest);
        messageRepository.save(userMessage);

        ChatbotExchangeDTO exchangeDTO = sendMessageOnHTTP(dtoRequest);
        ChatbotMessage chatbotResponse = messageBuilder(exchangeDTO);
        messageRepository.save(chatbotResponse);

        LOGGER.info("/Chatbot -> Storing the following message : {}", chatbotResponse);
        return exchangeDTO;
    }

    public ChatbotExchangeDTO sendMessageOnHTTP(ChatbotExchangeDTO dtoRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ChatbotExchangeDTO> entity = new HttpEntity<>(dtoRequest, headers);

        ChatbotExchangeDTO dtoResponse = restTemplate.exchange(
                chatbotUrl + "/message", HttpMethod.POST, entity, ChatbotExchangeDTO.class
        ).getBody();

        ChatbotExchangeDTO exchange = new ChatbotExchangeDTO();
        exchange.setMessageRequest(dtoRequest.getMessageRequest());
        exchange.setMessageResponse(dtoResponse.getMessageResponse());
        exchange.setConversationId(dtoRequest.getConversationId());
        exchange.setConversationId(dtoRequest.getConversationId());
        exchange.setChatbotRevision(dtoResponse.getChatbotRevision());
        exchange.setUserId(dtoResponse.getUserId());
        exchange.setInError(dtoResponse.getInError());
        return exchange;
    }

    public ChatbotMessage messageBuilder(ChatbotExchangeDTO dto) {
        ChatbotMessage message = new ChatbotMessage();
        if (dto.getMessageResponse() == null) {
            message.setText(dto.getMessageRequest());
        } else {
            message.setText(dto.getMessageResponse());
        }
        message.setInError(dto.getInError());
        message.setChatbotRevision(dto.getChatbotRevision());
        message.setConversationId(dto.getConversationId());
        message.setUserId(dto.getUserId());
        return message;
    }

    @Transactional
    public ChatbotStats calculateStats() {
        ChatbotStats stats = new ChatbotStats();
        ChatbotMessage lastMessage =
                messageRepository.findAll(Sort.by(Sort.Direction.DESC, "dateOfCreation")).get(0);

        float chatbotRevision = lastMessage.getChatbotRevision();
        stats.setChatbotRevision(chatbotRevision);
        Integer nbrMessages = messageRepository.countAllByChatbotRevision(chatbotRevision);
        stats.setNbrMessages(nbrMessages);
        Integer nbrError = messageRepository.countAllByChatbotRevisionAndInError(chatbotRevision, true);
        stats.setNbrerror(nbrError);
        float successInPercent = (nbrError.floatValue() / nbrMessages.floatValue()) * 100f;
        stats.setSuccessPercent((int) successInPercent);

        statsRepository.save(stats);
        return stats;
    }
}
