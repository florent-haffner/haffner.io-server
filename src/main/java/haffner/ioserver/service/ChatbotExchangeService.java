package haffner.ioserver.service;

import haffner.ioserver.data.domain.ChatbotExchange;
import haffner.ioserver.data.dto.ChatbotExchangeDTO;
import haffner.ioserver.exceptions.ChatbotResponseError;
import haffner.ioserver.repository.ChatbotExchangeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class ChatbotExchangeService {

    @Value("${chatbot.url}")
    private String chatbotUrl;

    private final ChatbotExchangeRepository repository;
    private final RestTemplate restTemplate;

    public ChatbotExchangeService(ChatbotExchangeRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public ChatbotExchange askThenStoreData(ChatbotExchangeDTO dtoRequest) throws ChatbotResponseError {
        ChatbotExchangeDTO dtoResponse = sendMessageViaHTTP(dtoRequest);

        ChatbotExchange chatbotExchange = new ChatbotExchange();
        chatbotExchange.setUserId(dtoRequest.getUserId());
        chatbotExchange.setMessageRequested(dtoRequest.getMessageRequested());
        chatbotExchange.setMessageResponse(dtoResponse.getMessageResponse());
        chatbotExchange.setInError(dtoResponse.getInError());

        repository.save(chatbotExchange);
        return chatbotExchange;
    }

    public ChatbotExchangeDTO sendMessageViaHTTP(ChatbotExchangeDTO dtoRequest) throws ChatbotResponseError {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ChatbotExchangeDTO> entity = new HttpEntity<>(dtoRequest, headers);

        ChatbotExchangeDTO dtoResponse = restTemplate.exchange(
            chatbotUrl + "/message", HttpMethod.POST, entity, ChatbotExchangeDTO.class
        ).getBody();

        if (dtoResponse == null) {
            throw new ChatbotResponseError("Error during request handling");
        }
        return dtoResponse;
    }

}
