package com.vtech.ecommerce.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public Map<String, Object> userDetails(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
            "message", "Acest endpoint este protejat!",
            "user", jwt.getClaims()
        );
    }
}