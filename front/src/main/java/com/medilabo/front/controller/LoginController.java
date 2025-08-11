package com.medilabo.front.controller;

import com.medilabo.front.services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class LoginController {

    @Autowired
    private WebClient webClient;

    @Autowired
    private LoginService loginService;

    private final Logger logger = Logger.getLogger(LoginController.class.getName());

    @GetMapping("/login")
    public String loginForm(@RequestParam(value="error", required=false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Identifiants invalides");
        }

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        // todo: refactor in service ?
        String basic = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

        try {
            // test call to gateway protected endpoint
            Map<String,String> resp = webClient.get()
                    .uri("http://localhost:9001/auth")
                    .headers(h -> h.set(HttpHeaders.AUTHORIZATION, basic))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                    })
                    .block(Duration.ofSeconds(5));

            logger.info("Response from auth endpoint: " + resp);
            System.out.println("Response from auth endpoint: " + resp);

            // auth OK -> sauvegarde du header d'auth en session (pour tests seulement)
            session.setAttribute("authHeader", basic);
            logger.info("User authenticated, authHeader saved in session id: " + session.getId());

            return "redirect:/patients";
        } catch (WebClientResponseException.Unauthorized ex) {
            model.addAttribute("error", "Identifiants invalides");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("authHeader");
        logger.info("User logged out, authHeader removed from session.");

        return "redirect:/login";
    }

}
