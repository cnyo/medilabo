package com.medilabo.front.controller;

import com.medilabo.front.beans.PatientBean;
import com.medilabo.front.exceptions.UnauthorizedFrontException;
import com.medilabo.front.services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class PatientController {
    private final WebClient webClient;

    private final Logger logger = Logger.getLogger(PatientController.class.getName());

    private final RestTemplate rest = new RestTemplate();

    private final LoginService loginService;

    public PatientController(WebClient.Builder builder, LoginService loginService) {
        this.webClient = builder.baseUrl("http://localhost:9001").build();
        this.loginService = loginService;
    }

    @GetMapping("/patients")
    public String index(HttpSession session, Model model) {

        try {
            logger.info("Accessing /patients endpoint");

            String auth = (String) session.getAttribute("authHeader");
            logger.info("Auth header from session: " + auth);
            if (auth == null) {
                return "redirect:/login";
            }

            List<PatientBean> patients = webClient.get()
                    .uri("/api/patients")
                    .headers(header -> header.set(HttpHeaders.AUTHORIZATION, auth))
                    .retrieve()
                    .bodyToFlux(PatientBean.class)
                    .collectList()
                    .block();

            model.addAttribute("patients", patients);

            return "index";
        } catch(UnauthorizedFrontException e) {
            return "redirect:/login?error";
        } catch (Exception e) {
            logger.info("Error fetching patients" + e.getMessage());
            return "redirect:/login?error";
        }
    }

    @GetMapping("/detail-patient/{id}")
    public String fichePatient(@PathVariable int id, HttpSession session, Model model) {
        loginService.isAuthenticated(session);
        String token = loginService.createHttpToken(session);

        PatientBean patient = webClient
                .get()
                .uri("/api/patients/" + id)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + token)
                .retrieve()
                .bodyToMono(PatientBean.class)
                .block();

        model.addAttribute("patient", patient);

        return "fichePatient";
    }

    @PostMapping(value="/update-patient/{id}")
    public String updatePatient(@PathVariable int id, @ModelAttribute PatientBean updatedPatient, HttpSession session) {
        try {
            loginService.isAuthenticated(session);
            String token = loginService.createHttpToken(session);

            ResponseEntity<PatientBean> response = webClient.put()
                    .uri("/api/patients/" + id)
                    .bodyValue(updatedPatient)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + token)
                    .retrieve()
                    .toEntity(PatientBean.class)
                    .block();

            // Todo: handle response status and errors

            return "redirect:/detail-patient/" + id;
        } catch (WebClientResponseException e) {
            // Par exemple : afficher un message d'erreur ou rediriger ailleurs
            return "error-page";
        }
    }
}
