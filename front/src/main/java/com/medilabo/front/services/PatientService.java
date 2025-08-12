package com.medilabo.front.services;

import com.medilabo.front.beans.PatientBean;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PatientService {

    private static final String BASE_PATH = "/api/patients";

    private final WebClient webClient;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PatientService.class);

    public PatientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PatientBean> getPatients(HttpSession session) {
        String auth = (String) session.getAttribute("authHeader");
        log.debug("Fetching patients with auth: {}", auth);

        return webClient.get()
                .uri(BASE_PATH)
                .headers(header -> header.set(HttpHeaders.AUTHORIZATION, auth))
                .retrieve()
                .bodyToFlux(PatientBean.class)
                .collectList()
                .block();
    }

    public PatientBean getPatientById(int id, HttpSession session) {
        String auth = (String) session.getAttribute("authHeader");

        return webClient
                .get()
                .uri(BASE_PATH + "/" + id)
                .header(HttpHeaders.AUTHORIZATION, auth)
                .retrieve()
                .bodyToMono(PatientBean.class)
                .block();
    }

    public ResponseEntity<PatientBean> updatePatient(int id, PatientBean updatedPatient, HttpSession session) {
        String auth = (String) session.getAttribute("authHeader");

        return webClient.put()
                .uri(BASE_PATH  + "/" + id)
                .bodyValue(updatedPatient)
                .header(HttpHeaders.AUTHORIZATION, auth)
                .retrieve()
                .toEntity(PatientBean.class)
                .block();
    }
}
