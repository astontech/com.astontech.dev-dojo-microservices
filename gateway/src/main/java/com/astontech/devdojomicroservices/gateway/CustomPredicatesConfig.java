package com.astontech.devdojomicroservices.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomPredicatesConfig {

    @Bean
    public LdapUserRoutePredicateFactory ldapUser() {
        return new LdapUserRoutePredicateFactory(LdapUserRoutePredicateFactory.Config.class);
    }
}
