package com.medilabo.front.services;

import com.medilabo.front.dto.NoteDto;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class NoteService {

    private final WebClient webClient;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NoteService.class);

    public NoteService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<NoteDto> getNotes(int patientId, HttpSession session) {
        try {
            String auth = (String) session.getAttribute("authHeader");
            log.debug("Fetching notes with auth: {}", auth);

            return webClient.get()
                    .uri("/api/patients/" + patientId + "/notes")
                    .headers(header -> header.set(HttpHeaders.AUTHORIZATION, auth))
                    .retrieve()
                    .bodyToFlux(NoteDto.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            log.error("Error fetching notes for patient with ID {}: {}", patientId, e.getMessage());
            return List.of();
        }

    }

    public ResponseEntity<NoteDto> addNote(String patientId, NoteDto note, HttpSession session) {
        String auth = (String) session.getAttribute("authHeader");

        return webClient.post()
                .uri("/api/patients/" + patientId + "/notes")
                .bodyValue(note)
                .header(HttpHeaders.AUTHORIZATION, auth)
                .retrieve()
                .toEntity(NoteDto.class)
                .block();
    }

    public NoteDto initNote(int patientId, String name) {
        NoteDto note = new NoteDto();
        note.setPatId(String.valueOf(patientId));
        note.setPatient(name);

        log.debug("Initialized new note for patient: {}", name);

        return note;
    }
}
