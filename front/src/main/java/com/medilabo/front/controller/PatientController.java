package com.medilabo.front.controller;

import com.medilabo.front.beans.PatientBean;
import com.medilabo.front.services.PatientService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Controller
public class PatientController {

    private final PatientService patientService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PatientController.class);

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String home() {
        log.info("Accessing home page");
        return "home";
    }

    @GetMapping("/patients")
    public String index(HttpSession session, Model model) {
        try {
            List<PatientBean> patients = patientService.getPatients(session);
            model.addAttribute("patients", patients);
            log.info("Patient list: {}", patients);

            return "patient_index";
        } catch (Exception e) {
            log.error("Error fetching patients: {}", e.getMessage());
            return "redirect:/login?error";
        }
    }

    @GetMapping("/detail-patient/{id}")
    public String fichePatient(@PathVariable int id, HttpSession session, Model model) {

        try {
            log.info("Patient detail: {}", id);
            PatientBean patient = patientService.getPatientById(id, session);
            model.addAttribute("patient", patient);

            return "fichePatient";
        } catch (Exception e) {
            log.error("Error fetching patient with ID {}: {}", id, e.getMessage());
            return "redirect:/login?error";
        }
    }

    @PostMapping(value="/update-patient/{id}")
    public String updatePatient(@PathVariable int id, @ModelAttribute PatientBean updatedPatient, HttpSession session) {
        try {
            ResponseEntity<PatientBean> response = patientService.updatePatient(id, updatedPatient, session);
            log.info("Patient updated: {}", response.getBody());

            // Todo: handle response status and errors

            return "redirect:/detail-patient/" + id;
        } catch (WebClientResponseException e) {
            log.error("Error to update patient with ID {}: {}", id, e.getMessage());
            return "redirect:/login?error";
        }
    }
}
