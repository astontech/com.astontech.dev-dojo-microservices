package com.astontech.devdojomicroservices.authserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.astontech.devdojomicroservices.authserver.LdapUser.*;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Slf4j
@Service
public class LdapService {

    private final LdapTemplate ldapTemplate;

    public LdapService(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
        this.ldapTemplate.setIgnorePartialResultException(true);
    }

    /**
     * Gets the LDAP user with the given email or null if no user is found.
     */
    public LdapUser findByUsername(String username) {
        String email = username + "@astontech.com";
        log.info("Retrieving LDAP user by email '{}'...", email);
        List<LdapUser> users = ldapTemplate.search(
                query().where("objectclass").is("person")
                        .and(LDAP_KEY_EMAIL).is(email),
                LDAP_USER_ATTRIBUTES_MAPPER)
                .stream()
                .filter(user -> user != null &&
                        user.getEmail() != null &&
                        user.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
        if (users == null || users.isEmpty()) {
            log.info("Did not find LDAP user with email '{}'.", email);
            return null;
        }
        log.info("Retrieved LDAP user: {}", users.get(0));
        return users.get(0);
    }

    private static final AttributesMapper<LdapUser> LDAP_USER_ATTRIBUTES_MAPPER = attributes -> {
        LdapUser user = new LdapUser()
                .setDisplayName(getAttributeValue(attributes.get(LDAP_KEY_DISPLAY_NAME)))
                .setEmail(getAttributeValue(attributes.get(LDAP_KEY_EMAIL)))
                .setRoles(getAttributeValues(attributes.get(LDAP_KEY_ROLES)));

        log.info("      {}", user.toString());

        return user;
    };

    private static String getAttributeValue(Attribute attribute) throws NamingException {
        return attribute == null ? "" : attribute.get().toString();
    }

    private static Set<String> getAttributeValues(Attribute attribute) throws NamingException {
        Set<String> values = new HashSet<>();
        if (attribute != null) {
            attribute.getAll().asIterator().forEachRemaining(item -> {
                // Example of a single "memberof" item:
                // CN=ADSyncAdmins,OU=Base,OU=Groups,DC=astontech,DC=com
                if (item.toString().contains("CN=")) {
                    values.add(item.toString().split("CN=")[1].split(",")[0]);
                }
            });
        }

        return values;
    }
}
