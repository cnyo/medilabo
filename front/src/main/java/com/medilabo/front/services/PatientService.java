package com.medilabo.front.services;

import com.medilabo.front.dto.PatientDto;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class PatientService {

    private static final String BASE_PATH = "/api/patients";

    private final WebClient webClient;

    private static final Logger log = getLogger(PatientService.class);

    public PatientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PatientDto> getPatients(HttpSession session) {
        try {
            String auth = (String) session.getAttribute("authHeader");
            log.debug("Fetching patients with auth: {}", auth);

            return webClient.get()
                    .uri(BASE_PATH)
                    .headers(header -> header.set(HttpHeaders.AUTHORIZATION, auth))
                    .retrieve()
                    .bodyToFlux(PatientDto.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            log.error("Error fetching patients: {}", e.getMessage());

            return new ArrayList<>();
        }
    }

    public PatientDto getPatientById(int id, HttpSession session) {
        try {
            String auth = (String) session.getAttribute("authHeader");

            return webClient
                    .get()
                    .uri(BASE_PATH + "/" + id)
                    .header(HttpHeaders.AUTHORIZATION, auth)
                    .retrieve()
                    .bodyToMono(PatientDto.class)
                    .block();
        } catch (Exception e) {
            log.error("Error fetching patient with ID {}: {}", id, e.getMessage());

            return null;
        }

    }

    public ResponseEntity<PatientDto> updatePatient(int id, PatientDto updatedPatient, HttpSession session) {
        try {
            String auth = (String) session.getAttribute("authHeader");

            return webClient.put()
                    .uri(BASE_PATH  + "/" + id)
                    .bodyValue(updatedPatient)
                    .header(HttpHeaders.AUTHORIZATION, auth)
                    .retrieve()
                    .toEntity(PatientDto.class)
                    .block();
        } catch (Exception e) {
            log.error("Error updating patient with ID {}: {}", id, e.getMessage());
            return null;
        }
    }
}
