package haffner.ioserver.controller;

import haffner.ioserver.data.domain.Message;
import haffner.ioserver.data.dto.MessageDTO;
import haffner.ioserver.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Message> sendMessageToChatbotMessage(@RequestBody MessageDTO dto) {
        Message message = new Message();
        message.setMessage(dto.getMessage());
        message.setInError(dto.getInError());

        repository.save(message);
        return ResponseEntity.ok(message);
    }
}
