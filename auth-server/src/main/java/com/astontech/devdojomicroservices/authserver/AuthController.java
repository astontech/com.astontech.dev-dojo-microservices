package com.astontech.devdojomicroservices.authserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LdapService ldapService;

    public AuthController(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<String>> getRoles(@RequestParam("username") String username) {
        try {
            LdapUser user = ldapService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.ok(Collections.EMPTY_SET);
            }
            return ResponseEntity.ok(user.getRoles());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
