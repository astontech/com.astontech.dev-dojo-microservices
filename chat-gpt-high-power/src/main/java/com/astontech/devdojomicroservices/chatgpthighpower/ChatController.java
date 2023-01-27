package com.astontech.devdojomicroservices.chatgpthighpower;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @GetMapping
    public ResponseEntity<String> getChatResponse() {
        return ResponseEntity.ok("hey hi hello what's up");
    }
}
