package haffner.io.server.service;

import haffner.io.server.data.domain.ChatbotMessage;
import haffner.io.server.data.dto.ChatbotExchangeDTO;
import haffner.io.server.repository.ChatbotMessageRepository;
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
        exchange.setInError(dtoResponse.getInError());
        exchange.setUserId(dtoResponse.getUserId());
        return exchange;
    }

    public ChatbotMessage messageBuilder(ChatbotExchangeDTO dto) {
        ChatbotMessage message = new ChatbotMessage();
        if (dto.getMessageResponse() == null) {
            message.setText(dto.getMessageRequest());
        } else {
            message.setText(dto.getMessageResponse());
        }
        message.setConversationId(dto.getConversationId());
        message.setUserId(dto.getUserId());
        return message;
    }

}
