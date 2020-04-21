package haffner.ioserver.service;

import haffner.ioserver.exceptions.ChatbotResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AppStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStateService.class);

    @Value("${chatbot.url}")
    private String chatbotUrl;

    public final RestTemplate restTemplate;

    public AppStateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<Map> getChatbotState() throws ChatbotResponseError {
        LOGGER.info("Requesting Chatbot API");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map response = restTemplate.exchange(chatbotUrl, HttpMethod.GET, entity, Map.class).getBody();

        if (response == null) {
            throw new ChatbotResponseError("Error during request handling");
        }
        return CompletableFuture.completedFuture(response);
    }

    public Map getEntireAppState() throws ExecutionException, InterruptedException, ChatbotResponseError {
        CompletableFuture<Map> chatbotResponse = getChatbotState();

        Map<String, Boolean> state = new HashMap<>();
        state.put("server_online", true);

        if (chatbotResponse.get().get("online_status").equals(true)) {
            state.put("chatbot_online", true);
        }
        return state;
    }

}
