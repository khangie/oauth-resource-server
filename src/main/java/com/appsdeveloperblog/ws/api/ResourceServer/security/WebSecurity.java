package com.appsdeveloperblog.ws.api.ResourceServer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    KeycloakRoleConverter keycloakRoleConverter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(keycloakRoleConverter);

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/status/check")
//                .hasAuthority("SCOPE_profile") // Continue to check JWT token only if endpoint and profile scope matches.  The prefix "SCOPE_" is default and "profile" is a standard scope.
                .hasRole("developer")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt() // Intercept http requests, extract/check JWT token
                .jwtAuthenticationConverter(jwtAuthenticationConverter); // Register JWT converter with HttpSecurity
    }
}
