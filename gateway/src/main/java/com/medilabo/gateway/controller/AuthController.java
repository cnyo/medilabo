package com.medilabo.gateway.controller;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class AuthController {
    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    @GetMapping("/auth")
    public Mono<Map<String,String>> auth() {
        Mono<Map<String,String>> auth = ReactiveSecurityContextHolder.getContext()
                .filter(ctx -> ctx.getAuthentication() != null)
                .map(ctx -> ctx.getAuthentication().getName())
                .map(name -> Collections.singletonMap("name", name))
                .switchIfEmpty(Mono.just(Collections.singletonMap("name", "anonymous")));

        logger.info("auth: " + auth);

        return auth;
    }
}
