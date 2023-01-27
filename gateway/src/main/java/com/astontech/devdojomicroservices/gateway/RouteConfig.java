package com.astontech.devdojomicroservices.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator astonRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("aston_route", r -> r
                        .path("/")
                        .filters(f -> f.addRequestHeader("Aston", "tech")
                                .circuitBreaker(config -> config
                                        .setName("aston_fallback")
                                        .setFallbackUri("forward:/local-fallback-response")
                                )
                        )
                        .uri("https://astontech.com/"))
                .build();
    }

}
