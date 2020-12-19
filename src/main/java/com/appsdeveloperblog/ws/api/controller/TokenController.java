package com.appsdeveloperblog.ws.api.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    // Bind the details of the current @AuthenticationPrincipal into jwt object
    @GetMapping
    public Jwt getToken(@AuthenticationPrincipal Jwt jwt) {

        // return contents of JWT token
        return jwt;
    }

}
