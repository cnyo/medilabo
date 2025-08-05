package com.medilabo.front.controller;

import com.medilabo.front.beans.PatientBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class FrontController {
    private final WebClient webClient;

    public FrontController(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:9001").build();
    }

    @GetMapping("/hello")
    public Mono<String> index() {
        return webClient.get()
                .uri("/api/hello")
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/")
    public String index(Model model) {
        List<PatientBean> patients = webClient.get()
                .uri("/api/patients")
                .retrieve()
                .bodyToFlux(PatientBean.class)
                .collectList()
                .block();

        model.addAttribute("patients", patients);

        return "index";
    }

    @GetMapping("/detail-patient/{id}")
    public String fichePatient(@PathVariable int id, Model model) {
        PatientBean patient = webClient.get()
                .uri("/api/patients/" + id)
                .retrieve()
                .bodyToMono(PatientBean.class)
                .block();

        model.addAttribute("patient", patient);

        return "fichePatient";
    }

    @PostMapping(value="/update-patient/{id}")
    public String updatePatient(@PathVariable int id, @ModelAttribute PatientBean updatedPatient) {
        try {
            ResponseEntity<PatientBean> response = webClient.put()
                    .uri("/api/patients/" + id)
                    .bodyValue(updatedPatient)
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
