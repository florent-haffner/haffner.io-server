package haffner.ioserver.controller;

import haffner.ioserver.data.domain.Message;
import haffner.ioserver.data.dto.MessageDTO;
import haffner.ioserver.repository.MessageRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Message> storeMessageToChatbot(@RequestBody MessageDTO dto) {
        Message message = new Message();
        message.setMessage(dto.getMessage());
        message.setInError(dto.getInError());

        repository.save(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping("sendMessage")
    public ResponseEntity<String> sendMessageToChatbot(@RequestBody MessageDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<MessageDTO> entity = new HttpEntity<MessageDTO>(dto,headers);

        String response = restTemplate.exchange(
            "https://haffner-chatbot.herokuapp.com/message", HttpMethod.POST, entity, String.class).getBody();
        assert response != null;
        return ResponseEntity.ok(response);
    }
}
