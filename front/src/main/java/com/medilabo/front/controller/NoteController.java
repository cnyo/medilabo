package com.medilabo.front.controller;

import com.medilabo.front.dto.NoteDto;
import com.medilabo.front.services.NoteService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    private static final Logger log = LoggerFactory.getLogger(NoteController.class);

    @PostMapping(value="/patients/{patientId}/note")
    public String addNote(@PathVariable String patientId, @ModelAttribute NoteDto newNote, HttpSession session) {
        try {
            ResponseEntity<NoteDto> response = noteService.addNote(patientId, newNote, session);
            log.info("New note: {}", response.getBody());

            // Todo: handle response status and errors

            return "redirect:/patients/" + patientId;
        } catch (WebClientResponseException e) {
            log.error("Error to add note for patient with ID {}: {}", patientId, e.getMessage());

            return "redirect:/login?error";
        }
    }
}
