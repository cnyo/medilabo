package com.medilabo.front.controller;

import com.medilabo.front.dto.AssessmentDto;
import com.medilabo.front.dto.NoteDto;
import com.medilabo.front.dto.PatientDto;
import com.medilabo.front.services.AssessmentService;
import com.medilabo.front.services.NoteService;
import com.medilabo.front.services.PatientService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientController {

    private final PatientService patientService;
    private final NoteService noteService;
    private final AssessmentService assessmentService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PatientController.class);

    public PatientController(PatientService patientService, NoteService noteService, AssessmentService assessmentService) {
        this.patientService = patientService;
        this.noteService = noteService;
        this.assessmentService = assessmentService;
    }

    @GetMapping("/")
    public String home() {
        log.info("Accessing home page");
        return "home";
    }

    @GetMapping("/patients")
    public String index(HttpSession session, Model model) {
        try {
            List<PatientDto> patients = patientService.getPatients(session);
            model.addAttribute("patients", patients);
            log.info("Patient list: {}", patients);

            return "patient_index";
        } catch (Exception e) {
            log.error("Error fetching patients: {}", e.getMessage());
            return "redirect:/login?error";
        }
    }

    @GetMapping("/patients/{id}")
    public String fichePatient(@PathVariable int id, HttpSession session, Model model) {

        try {
            log.info("Patient details: {}", id);
            PatientDto patient = patientService.getPatientById(id, session);
            NoteDto newNote = noteService.initNote(id, patient.getName());
            List<NoteDto> notes = noteService.getNotes(id, session);
            AssessmentDto assessment = assessmentService.getAssessmentForPatient(session, patient);

            model.addAttribute("patient", patient);
            model.addAttribute("newNote", newNote);
            model.addAttribute("notes", notes);
            model.addAttribute("assessment", assessment);

            return "patient";
        } catch (Exception e) {
            log.error("Error fetching patient with ID {}: {}", id, e.getMessage());
            return "redirect:/login?error";
        }
    }

    @PostMapping(value="/patients/{id}")
    public String updatePatient(
            @PathVariable int id,
            @ModelAttribute PatientDto updatedPatient,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            ResponseEntity<PatientDto> response = patientService.updatePatient(id, updatedPatient, session);
            log.info("Patient updated: {}", response.getBody());
            redirectAttributes.addFlashAttribute("successMessage", "Patient mise à jour avec succès");
        } catch (WebClientResponseException e) {
            log.error("Error to update patient with ID {}: {}", id, e.getMessage());
            redirectAttributes.addAttribute("errorMessage", "Erreur lors de la mise à jour du patient: " + e.getResponseBodyAsString());
        }

        return "redirect:/patients/{id}";
    }
}
