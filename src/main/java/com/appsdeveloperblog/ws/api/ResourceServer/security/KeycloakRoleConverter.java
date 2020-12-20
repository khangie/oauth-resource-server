package com.appsdeveloperblog.ws.api.ResourceServer.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Converts a Jwt into a collection of Granted Authorities
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        // Extract the realm_access section from the JWT token and extract it into a Map.
        // "realm_access": {
        //    "roles": [
        //      "offline_access",
        //      "developer",
        //      "uma_authorization"
        //    ]
        //  },
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        // If Map is empty, return an empty arraylist
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }

        // Extract the list of roles from the Map
        // - offline_access
        // - developer
        // - uma_authorization
        List<String> roleList = (List<String>) realmAccess.get("roles");

        Collection<GrantedAuthority> returnValue = roleList.stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return returnValue;
    }
}
