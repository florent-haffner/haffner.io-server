package haffner.ioserver.controller;

import haffner.ioserver.data.domain.Message;
import haffner.ioserver.data.dto.MessageDTO;
import haffner.ioserver.exceptions.ChatbotResponseError;
import haffner.ioserver.repository.MessageRepository;
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
@RequestMapping("/message")
public class MessageController {

    private MessageRepository repository;
    private RestTemplate restTemplate;

    public MessageController(MessageRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @PutMapping
    public ResponseEntity<Message> askChatbotThenStoreData(@RequestBody MessageDTO dtoRequest) throws ChatbotResponseError {
        MessageDTO dtoResponse = sendMessageToChatbot(dtoRequest).getBody();

        Message message = new Message();
        message.setMessageRequested(dtoRequest.getMessageRequested());
        message.setMessageResponse(dtoResponse.getMessageResponse());
        message.setInError(dtoResponse.getInError());

        repository.save(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping("sendToChatbot")
    public ResponseEntity<MessageDTO> sendMessageToChatbot(@RequestBody MessageDTO dtoRequest) throws ChatbotResponseError {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<MessageDTO> entity = new HttpEntity<>(dtoRequest,headers);

        MessageDTO dtoResponse = restTemplate.exchange(
            "https://haffner-chatbot.herokuapp.com/message", HttpMethod.POST, entity, MessageDTO.class).getBody();

        if (dtoResponse == null) {
            throw new ChatbotResponseError("Error during request handling");
        }
        return ResponseEntity.ok(dtoResponse);
    }

}
