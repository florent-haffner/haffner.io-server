package haffner.io.server.service;

import haffner.io.server.controller.ChatbotMessageController;
import haffner.io.server.data.domain.ChatbotMessage;
import haffner.io.server.data.dto.ChatbotExchangeDTO;
import haffner.io.server.repository.ChatbotMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ChatbotMessageService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotMessageController.class);


    @Value("${chatbot.url}")
    private String chatbotUrl;

    private final ChatbotMessageRepository repository;
    private final RestTemplate restTemplate;

    public ChatbotMessageService(ChatbotMessageRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<ChatbotMessage> findAllByConversationId(String conversationId) {
        return repository.findByConversationId(conversationId);
    }

    public ChatbotExchangeDTO askThenStoreData(ChatbotExchangeDTO dtoRequest) {
        ChatbotMessage userMessage = messageBuilder(dtoRequest);
        repository.save(userMessage);

        ChatbotExchangeDTO exchangeDTO = sendMessageOnHTTP(dtoRequest);
        ChatbotMessage chatbotResponse = messageBuilder(exchangeDTO);
        repository.save(chatbotResponse);

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
        message.setIn_error(dto.getInError());
        message.setChatbotRevision(dto.getChatbotRevision());
        message.setConversationId(dto.getConversationId());
        message.setUserId(dto.getUserId());
        return message;
    }

}
