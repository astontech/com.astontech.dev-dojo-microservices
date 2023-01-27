package com.astontech.devdojomicroservices.chatgptlowpower;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @GetMapping
    public ResponseEntity<String> getServerAtCapacityMessage() throws InterruptedException {
        TimeUnit.SECONDS.sleep(7);
        return ResponseEntity.status(503).body("you tried accessing chatGPT DURING THE DAY? HA. are you insane? here's an AI generated poem about how dense you are for thinking you could ever log in between the hours of 5am - 10pm...");
    }
}
