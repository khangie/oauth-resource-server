package com.appsdeveloperblog.ws.api.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/status/check")
    public String status() {
        return "Working...";
    }

    // Use @AuthenticationPrincipal to inject Jwt token into method.  We use the "sub" claim from the JWT to get the user_id.
    // Note that #id refers to the input argument @PathVariable("id)
    //    @Secured("ROLE_developer")
    //    @PreAuthorize("hasRole('developer')")
    @PreAuthorize("hasAuthority('ROLE_developer') or #id == #jwt.subject")
    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable("id") final String id, @AuthenticationPrincipal Jwt jwt) {
        return "Deleted user with id=" + id + " and JWT subject = " + jwt.getSubject();
    }

}
