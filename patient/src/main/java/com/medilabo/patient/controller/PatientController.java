package com.medilabo.patient.controller;

import com.medilabo.patient.configurations.ApplicationPropertiesConfiguration;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.exceptions.PatientNotFoundException;
import com.medilabo.patient.exceptions.PatientAlreadyExistsException;
import com.medilabo.patient.services.JsonFilterService;
import com.medilabo.patient.services.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.tags.Tag;

import static org.slf4j.LoggerFactory.getLogger;

@Tag(name = "Patient", description = "Patient management operations")
@RestController
public class PatientController {
    private static final String PATIENT_FILTER = "patientFilter";

    private final PatientService patientService;
    private final JsonFilterService jsonFilterService;
    private final ApplicationPropertiesConfiguration appProperties;

    private final Logger log = getLogger(PatientController.class);

    public PatientController(PatientService patientService, JsonFilterService jsonFilterService, ApplicationPropertiesConfiguration appProperties) {
        this.patientService = patientService;
        this.jsonFilterService = jsonFilterService;
        this.appProperties = appProperties;
    }

    @Tag(name = "find all")
    @Tag(name = "findAllPatients", description = "Retrieve a list of all patients")
    @GetMapping("/patients")
    public MappingJacksonValue getPatientList() {
        log.info("Get all patient list");
        List<Patient> patients = patientService.findAll();
        List<Patient> limitedListPatient = patients.subList(0, appProperties.getPatientsLimit());
        log.info("Retrieved {} patients", limitedListPatient.size());

        return jsonFilterService.filterProperties(patients, PATIENT_FILTER, "createdAt", "updatedAt");
    }

    @Tag(name = "find")
    @Tag(name = "findPatient", description = "Find a patient by ID")
    @GetMapping("/patients/{id}")
    public MappingJacksonValue getPatient(@PathVariable int id) {
        Patient patient = patientService.findById(id);

        if (Objects.isNull(patient)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }

        return jsonFilterService.filterProperties(patient, PATIENT_FILTER, "createdAt", "updatedAt");
    }

    @Tag(name = "createPatient", description = "Create a new patient")
    @PostMapping("/patients")
    public ResponseEntity<String> addPatient(@Valid @RequestBody Patient patient) {
        try {
            log.info("Adding new patient: {}", patient);
            Patient patientAdded = patientService.save(patient);

            if (Objects.isNull(patientAdded)) {
                return ResponseEntity.badRequest().build();
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(patientAdded.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (PatientAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A patient with the same data already exists.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data integrity violation: possible duplicate or constraint error.");
        }

    }

    @Tag(name = "updatePatient", description = "Update an existing patient")
    @PutMapping("/patients/{id}")
    public ResponseEntity<MappingJacksonValue> updatePatient(@PathVariable int id, @Valid @RequestBody Patient patient) {
        log.info("Updating patient with id: {}", id);
        Patient updatedPatient = patientService.update(id, patient);

        if (Objects.isNull(updatedPatient)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(jsonFilterService.filterProperties(updatedPatient, PATIENT_FILTER, "createdAt", "updatedAt"));
    }
}
