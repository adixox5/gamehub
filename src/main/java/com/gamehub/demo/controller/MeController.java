package com.gamehub.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MeController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        if (auth == null) {
            return Map.of("authenticated", false);
        }
        return Map.of(
                "authenticated", auth.isAuthenticated(),
                "name", auth.getName(),
                "authorities", auth.getAuthorities()
        );
    }
}
