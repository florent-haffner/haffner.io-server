package haffner.ioserver.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/chatbot")
public class ChatbotExchangeController {

    @Value("${chatbot.url}")
    private String chatbotUrl;

    private final ChatbotExchangeRepository repository;
    private final RestTemplate restTemplate;

    public ChatbotExchangeController(ChatbotExchangeRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @PutMapping
    public ResponseEntity<ChatbotExchange> askThenStoreData(@RequestBody ChatbotExchangeDTO dtoRequest) throws ChatbotResponseError {
        ChatbotExchangeDTO dtoResponse = sendMessageViaHTTP(dtoRequest).getBody();

        ChatbotExchange chatbotExchange = new ChatbotExchange();
        chatbotExchange.setUserId(dtoRequest.getUserId());
        chatbotExchange.setMessageRequested(dtoRequest.getMessageRequested());
        chatbotExchange.setMessageResponse(dtoResponse.getMessageResponse());
        chatbotExchange.setInError(dtoResponse.getInError());

        repository.save(chatbotExchange);
        return ResponseEntity.ok(chatbotExchange);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<ChatbotExchangeDTO> sendMessageViaHTTP(@RequestBody ChatbotExchangeDTO dtoRequest) throws ChatbotResponseError {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ChatbotExchangeDTO> entity = new HttpEntity<>(dtoRequest, headers);

        ChatbotExchangeDTO dtoResponse = restTemplate.exchange(
            chatbotUrl + "/message", HttpMethod.POST, entity, ChatbotExchangeDTO.class
        ).getBody();

        if (dtoResponse == null) {
            throw new ChatbotResponseError("Error during request handling");
        }
        return ResponseEntity.ok(dtoResponse);
    }

}
