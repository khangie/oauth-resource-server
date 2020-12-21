package com.appsdeveloperblog.ws.api.controller;

import com.appsdeveloperblog.ws.api.response.UserRest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
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

    // returnObject is a default keyword that refers to the return value of the method
    // Hard code sub from JWT token which is the user_id
    @PostAuthorize("returnObject.userId == #jwt.subject")
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable("id") final String id, @AuthenticationPrincipal Jwt jwt) {
        return new UserRest("John", "Doe", "bedb6f0e-cdab-4cda-b55e-c51e15d2c68d");
    }

}
