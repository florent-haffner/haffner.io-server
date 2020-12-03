package haffner.io.server.controller;

import haffner.io.server.data.domain.ChatbotStats;
import haffner.io.server.service.ChatbotService;
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
public class ChatbotController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotController.class);

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<ChatbotMessage>> findAllMessagesByConversationId(@PathVariable String conversationId) {
        LOGGER.info("/Chatbot -> Asking messages by conversation: {}", conversationId);
        List<ChatbotMessage> messages = chatbotService.findAllByConversationId(conversationId);
        return ResponseEntity.ok(messages);
    }

    @PutMapping
    public ResponseEntity<ChatbotExchangeDTO> askThenStoreData(@RequestBody ChatbotExchangeDTO dtoRequest) {
        LOGGER.info("/Chatbot -> Ask then store the following conversation: {}", dtoRequest);
        ChatbotExchangeDTO exchange = chatbotService.askThenStoreData(dtoRequest);
        return ResponseEntity.ok(exchange);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<ChatbotExchangeDTO> sendMessageViaHTTP(@RequestBody ChatbotExchangeDTO dtoRequest) {
        LOGGER.info("/Chatbot -> Sending the following message : {}", dtoRequest);
        ChatbotExchangeDTO dtoResponse = chatbotService.sendMessageOnHTTP(dtoRequest);
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/stats/")
    public ResponseEntity<List<ChatbotStats>> getStats() {
        LOGGER.info("/Chatbot/stats/ -> Get all stats available.");
        List<ChatbotStats> stats = chatbotService.getStats();
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/stats/calculate")
    public ResponseEntity<ChatbotStats> calculateStats() {
        LOGGER.info("/Chatbot/stats/calculate -> Calculate stats for the current day.");
        ChatbotStats stats = chatbotService.calculateStats();
        return ResponseEntity.ok(stats);
    }

}
