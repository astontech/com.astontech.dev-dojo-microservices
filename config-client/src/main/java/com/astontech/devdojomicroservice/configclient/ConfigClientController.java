package com.astontech.devdojomicroservice.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigClientController {

    @Value("${user.role}")
    private String role;

    @GetMapping(value = "/role", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getConfiguredRole() {
        return role;
    }
}
