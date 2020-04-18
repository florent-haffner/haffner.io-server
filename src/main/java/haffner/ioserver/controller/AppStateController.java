package haffner.ioserver.controller;

import haffner.ioserver.exceptions.ChatbotResponseError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class AppStateController {

    @Value("${chatbot.url}")
    private String chatbotUrl;

    public final RestTemplate restTemplate;

    public AppStateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Map<String, Boolean>> getChatbotState() throws ChatbotResponseError {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map response = restTemplate.exchange(chatbotUrl, HttpMethod.GET, entity, Map.class).getBody();

        if (response == null) {
            throw new ChatbotResponseError("Error during request handling");
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<Map<String, Boolean>> getEntireAppState() throws ChatbotResponseError {
        Map chatbotResponse = getChatbotState().getBody();

        Map<String, Boolean> state = new HashMap<>();
        state.put("server_online", true);

        if (chatbotResponse.get("online_status").equals(true)) {
            state.put("chatbot_online", true);
        }

        return ResponseEntity.ok(state);
    }
}
