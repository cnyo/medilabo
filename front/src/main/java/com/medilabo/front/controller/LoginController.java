package com.medilabo.front.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(@RequestParam(value="error", required=false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Identifiants invalides");
        }

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // Pour tests : on accepte uniquement user/user
        if ("user".equals(username) && "user".equals(password)) {
            session.setAttribute("username", username);
            session.setAttribute("password", password); // -> seulement pour tests
            return "redirect:/patients";
        } else {
            return "redirect:/login?error";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/login";
    }

}
