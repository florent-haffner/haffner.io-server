package haffner.ioserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class AppStateController {

    public AppStateController() { }

    @GetMapping
    public ResponseEntity<Map<String, String>> getAppState() {
        Map<String, String> state = new HashMap<>();
        state.put("online_status", String.valueOf(true));
        return ResponseEntity.ok(state);
    }
}
