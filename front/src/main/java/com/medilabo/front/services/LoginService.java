package com.medilabo.front.services;

import com.medilabo.front.exceptions.UnauthorizedFrontException;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;

@Service
public class LoginService {
    private final WebClient webClient;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PatientService.class);

    public LoginService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void isAuthenticated(HttpSession session) {
        String username = session.getAttribute("username").toString();
        String password = session.getAttribute("password").toString();

        if (username == null || password == null) {
            throw new UnauthorizedFrontException("User not authenticated");
        }

        if (!("user".equals(username) && "user".equals(password))) {
            throw new UnauthorizedFrontException("User not authenticated");
        }
    }

    public String createHttpToken(HttpSession session) {
        String username = session.getAttribute("username").toString();
        String password = session.getAttribute("password").toString();

        String token = Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + token);

        return Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    public Map<String, String> getLoginResponse(String username, String password, String basic) {
        log.debug("getLoginResponse");

        return webClient.get()
            .uri("http://gateway:9001/auth")
            .headers(h -> h.set(HttpHeaders.AUTHORIZATION, basic))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
            })
            .block(Duration.ofSeconds(5));
    }
}
