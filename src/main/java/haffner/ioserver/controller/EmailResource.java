package haffner.ioserver.controller;

import haffner.ioserver.data.EmailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailResource {

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO dto) {
        String response = "Email: " + dto.getEmail() + ", message: " + dto.getMessage();
        return ResponseEntity.ok(response);
    }

}
