package haffner.ioserver.controller;

import haffner.ioserver.data.domain.ChatbotExchange;
import haffner.ioserver.data.dto.ChatbotExchangeDTO;
import haffner.ioserver.exceptions.ChatbotResponseError;
import haffner.ioserver.service.ChatbotExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
public class ChatbotExchangeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotExchangeController.class);

    private final ChatbotExchangeService chatbotExchangeService;

    public ChatbotExchangeController(ChatbotExchangeService chatbotExchangeService) {
        this.chatbotExchangeService = chatbotExchangeService;
    }

    @PutMapping
    public ResponseEntity<ChatbotExchange> askThenStoreData(@RequestBody ChatbotExchangeDTO dtoRequest) throws ChatbotResponseError {
        LOGGER.info("/Chatbot -> Asking then storing the following message : {}", dtoRequest);
        ChatbotExchange exchange = chatbotExchangeService.askThenStoreData(dtoRequest);
        return ResponseEntity.ok(exchange);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<ChatbotExchangeDTO> sendMessageViaHTTP(@RequestBody ChatbotExchangeDTO dtoRequest) throws ChatbotResponseError {
        LOGGER.info("/Chatbot -> Sending the following message to chatbot : {}", dtoRequest);
        ChatbotExchangeDTO dtoResponse = chatbotExchangeService.sendMessageViaHTTP(dtoRequest);
        return ResponseEntity.ok(dtoResponse);
    }

}
