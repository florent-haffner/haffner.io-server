package haffner.ioserver.controller;

import haffner.ioserver.exceptions.ChatbotResponseError;
import haffner.ioserver.service.AppStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping
public class AppStateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStateController.class);

    private final AppStateService appStateService;

    public AppStateController(AppStateService appStateService) {
        this.appStateService = appStateService;
    }

    @GetMapping
    public ResponseEntity<Map> getEntireAppState() throws ChatbotResponseError, ExecutionException, InterruptedException {
        LOGGER.info("Index -> Requesting APIs for state information");
        Map state = appStateService.getEntireAppState();
        return ResponseEntity.ok(state);
    }

}
