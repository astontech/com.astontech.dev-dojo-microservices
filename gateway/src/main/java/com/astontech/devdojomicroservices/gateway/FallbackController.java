package com.astontech.devdojomicroservices.gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/local-fallback-response")
    public Mono<String> fallback() {
        return Mono.just("here's a fallback response");
    }
}
