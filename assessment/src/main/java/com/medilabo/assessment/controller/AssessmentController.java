package com.medilabo.assessment.controller;

import com.medilabo.assessment.proxies.note.MicroserviceNoteProxy;
import com.medilabo.assessment.proxies.note.NoteDto;
import com.medilabo.assessment.proxies.patient.MicroservicePatientProxy;
import com.medilabo.assessment.proxies.patient.PatientDto;
import com.medilabo.assessment.services.AssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AssessmentController {

    private final MicroservicePatientProxy patientProxy;
    private final MicroserviceNoteProxy noteProxy;
    private final AssessmentService assessmentService;

    private static final Logger log = LoggerFactory.getLogger(AssessmentController.class);

    public AssessmentController(MicroservicePatientProxy patientProxy, MicroserviceNoteProxy noteProxy, AssessmentService assessmentService) {
        this.patientProxy = patientProxy;
        this.noteProxy = noteProxy;
        this.assessmentService = assessmentService;
    }

    @GetMapping("/assessment/{patientId}")
    public ResponseEntity<String> generateAssessment(@PathVariable int patientId) {
        log.info("Generating assessment for patient {}", patientId);
        PatientDto patient = patientProxy.getPatientById(patientId);
        List<NoteDto> notes = noteProxy.getNotesByPatientId(String.valueOf(patientId));
        log.debug("notes found: {}", notes.size());

        return ResponseEntity.ok(assessmentService.processAssessment(patient, notes));
    }
}
