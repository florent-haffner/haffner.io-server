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
import org.springframework.stereotype.Service;
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
        ChatbotExchangeDTO exchangeDTO = sendMessageViaHTTP(dtoRequest);

        ChatbotExchange chatbotExchange = new ChatbotExchange();
        chatbotExchange.setUserId(exchangeDTO.getUserId());
        chatbotExchange.setMessageRequested(exchangeDTO.getMessageRequested());
        chatbotExchange.setMessageResponse(exchangeDTO.getMessageResponse());
        chatbotExchange.setInError(exchangeDTO.getInError());

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

        ChatbotExchangeDTO exchange = new ChatbotExchangeDTO();
        exchange.setUserId(dtoRequest.getUserId());
        exchange.setMessageRequested(dtoRequest.getMessageRequested());
        exchange.setMessageResponse(dtoResponse.getMessageResponse());
        exchange.setInError(dtoResponse.getInError());

        if (dtoResponse == null) {
            throw new ChatbotResponseError("Error during request handling");
        }
        return exchange;
    }

}
