package com.medilabo.front.controller;

import com.medilabo.front.services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Controller
public class LoginController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            log.error("Login error: {}", error);
            model.addAttribute("error", "Identifiants invalides");
        }

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {

        String basic = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

        try {
            // test call to gateway protected endpoint
            Map<String, String> resp = loginService.getLoginResponse(username, password, basic);
            resp.forEach((k, v) -> log.info("Response: {} = {}", k, v));

            if (resp.isEmpty() || !resp.containsKey("name")) {
                log.error("Login failed, no response received from gateway");
                model.addAttribute("error", "Identifiants invalides");
                return "login?error";
            }

            // Save the auth header in session
            session.setAttribute("authHeader", basic);
            log.info("User authenticated, authHeader saved in session id: {}", session.getId());

            return "redirect:/patients";
        } catch (WebClientResponseException.Unauthorized ex) {
            model.addAttribute("error", "Identifiants invalides");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("authHeader");
        log.info("User logged out, authHeader removed from session.");

        return "redirect:/login";
    }

}
