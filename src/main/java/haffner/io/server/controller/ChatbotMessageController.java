package haffner.io.server.controller;

import haffner.io.server.data.domain.ChatbotStats;
import haffner.io.server.service.ChatbotMessageService;
import haffner.io.server.data.domain.ChatbotMessage;
import haffner.io.server.data.dto.ChatbotExchangeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ChatbotExchangeDTO> askThenStoreData(@RequestBody ChatbotExchangeDTO dtoRequest) {
        LOGGER.info("/Chatbot -> Ask then store the following conversation: {}", dtoRequest);
        ChatbotExchangeDTO exchange = chatbotMessageService.askThenStoreData(dtoRequest);
        return ResponseEntity.ok(exchange);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<ChatbotExchangeDTO> sendMessageViaHTTP(@RequestBody ChatbotExchangeDTO dtoRequest) {
        LOGGER.info("/Chatbot -> Sending the following message : {}", dtoRequest);
        ChatbotExchangeDTO dtoResponse = chatbotMessageService.sendMessageOnHTTP(dtoRequest);
        return ResponseEntity.ok(dtoResponse);
    }

    @PutMapping("/stats/calculate")
    public ResponseEntity<ChatbotStats> calculateStats() {
        LOGGER.info("/Chatbot -> Calculate stats for the current day");
        ChatbotStats stats = chatbotMessageService.calculateStats();
        return ResponseEntity.ok(stats);
    }

}
