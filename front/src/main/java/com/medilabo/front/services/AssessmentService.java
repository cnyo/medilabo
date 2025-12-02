package com.medilabo.front.services;

import com.medilabo.front.dto.AssessmentDto;
import com.medilabo.front.dto.PatientDto;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AssessmentService {
    private static final String BASE_PATH = "/api/assessment";
    private final WebClient webClient;
    private static final Logger log = getLogger(AssessmentService.class);

    public AssessmentService(WebClient webClient) {
        this.webClient = webClient;
    }

    public AssessmentDto getAssessmentForPatient(HttpSession session, PatientDto patient) {
        try {
            if (patient == null) {
                log.error("Patient is null, cannot fetch assessment.");
                throw new RuntimeException("Patient is null, cannot fetch assessment.");
            }

            String auth = (String) session.getAttribute("authHeader");
            log.debug("Fetching assessment for patient {} with auth: {}", patient.getId(), auth);

            return webClient.get()
                    .uri(BASE_PATH + "/" + patient.getId())
                    .headers(header -> header.set(HttpHeaders.AUTHORIZATION, auth))
                    .retrieve()
                    .bodyToMono(AssessmentDto.class)
                    .block();
        } catch (Exception e) {
            log.error("Error validating patient: {}", e.getMessage());
            return new AssessmentDto();
        }
    }
}
