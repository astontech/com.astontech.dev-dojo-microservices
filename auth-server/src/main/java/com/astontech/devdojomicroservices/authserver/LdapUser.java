package com.astontech.devdojomicroservices.authserver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public final class LdapUser {

    public static final String LDAP_KEY_DISPLAY_NAME = "displayname";
    private String displayName;

    public static final String LDAP_KEY_EMAIL = "userprincipalname";
    private String email;

    public static final String LDAP_KEY_ROLES = "memberof";
    private Set<String> roles;

}
