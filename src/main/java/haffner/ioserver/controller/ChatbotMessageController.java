package haffner.ioserver.controller;

import haffner.ioserver.data.domain.ChatbotMessage;
import haffner.ioserver.data.dto.ChatbotExchangeDTO;
import haffner.ioserver.exceptions.ChatbotResponseError;
import haffner.ioserver.service.ChatbotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatbot")
public class ChatbotMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotMessageController.class);

    private final ChatbotMessageService chatbotMessageService;

    public ChatbotMessageController(ChatbotMessageService chatbotMessageService) {
        this.chatbotMessageService = chatbotMessageService;
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<ChatbotMessage>> findAllMessagesByConversationId(@PathVariable String conversationId) {
        LOGGER.info("/Chatbot -> Asking messages by conversation: {}", conversationId);
        List<ChatbotMessage> messages = chatbotMessageService.findAllByConversationId(conversationId);
        return ResponseEntity.ok(messages);
    }

    @PutMapping
    public ResponseEntity<ChatbotMessage> askThenStoreData(@RequestBody ChatbotExchangeDTO dtoRequest) throws ChatbotResponseError {
        LOGGER.info("/Chatbot -> Ask then store the following message : {}", dtoRequest);
        ChatbotMessage exchange = chatbotMessageService.askThenStoreData(dtoRequest);
        return ResponseEntity.ok(exchange);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<ChatbotExchangeDTO> sendMessageViaHTTP(@RequestBody ChatbotExchangeDTO dtoRequest) throws ChatbotResponseError {
        LOGGER.info("/Chatbot -> Sending the following message : {}", dtoRequest);
        ChatbotExchangeDTO dtoResponse = chatbotMessageService.sendMessageViaHTTP(dtoRequest);
        return ResponseEntity.ok(dtoResponse);
    }

}
