package com.medilabo.front.services;

import com.medilabo.front.exceptions.UnauthorizedFrontException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class LoginService {

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

}
