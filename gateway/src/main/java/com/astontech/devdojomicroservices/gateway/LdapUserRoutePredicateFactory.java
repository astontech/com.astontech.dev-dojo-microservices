package com.astontech.devdojomicroservices.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;
import java.util.function.Predicate;

public class LdapUserRoutePredicateFactory extends AbstractRoutePredicateFactory<LdapUserRoutePredicateFactory.Config> {

    @Value("${auth.server.roles-endpoint}")
    private String authServerRolesEndpoint;

    public LdapUserRoutePredicateFactory(Class<Config> configClass) {
        super(configClass);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (ServerWebExchange t) -> {
            List<String> usernameHeaderContent = t.getRequest().getHeaders().get("username");
            if (usernameHeaderContent == null || usernameHeaderContent.isEmpty()) {
                return !Boolean.parseBoolean(config.match);
            }

            ResponseEntity<String> response = new RestTemplate()
                    .getForEntity(authServerRolesEndpoint + "?username=" + usernameHeaderContent.get(0), String.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                return false;
            }

            boolean hasRole = Arrays.stream(response.getBody().split(",")).anyMatch(r -> r.trim().replace("\"", "").equalsIgnoreCase(config.requiredRole));
            return Boolean.parseBoolean(config.match) == hasRole;
        };
    }

    @Validated
    public static class Config {

        public Config(String requiredRole, String match) {
            this.requiredRole = requiredRole;
            this.match = match;
        }
        String requiredRole;
        String match;
    }
}
