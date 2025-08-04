package com.medilabo.patient.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.medilabo.patient.configurations.ApplicationPropertiesConfiguration;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.web.exceptions.PatientNotFoundException;
import com.medilabo.patient.web.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Patient", description = "Patient management operations")
@RestController
public class PatientController {

    private final PatientService patientService;

    private final ApplicationPropertiesConfiguration appProperties;

    @Value("${server.instance.id}")
    String instanceId;

    public PatientController(PatientService patientService, ApplicationPropertiesConfiguration appProperties) {
        this.patientService = patientService;
        this.appProperties = appProperties;
    }

    @GetMapping("/hello")
    public String hello() {
        return String.format("Hello from instance %s", instanceId);
    }

    @Tag(name = "find all")
    @Tag(name = "findAllPatients", description = "Retrieve a list of all patients")
    @GetMapping("/patients")
    public MappingJacksonValue getPatientList() {
        List<Patient> patients = patientService.findAll();
        List<Patient> limitedListPatient = patients.subList(0, appProperties.getPatientsLimit());

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("createdAt", "updatedAt");
        FilterProvider filters = new SimpleFilterProvider().addFilter("patientFilter", filter);
        MappingJacksonValue patientsFilters = new MappingJacksonValue(limitedListPatient);
        patientsFilters.setFilters(filters);

        return patientsFilters;
    }

    @Tag(name = "find")
    @Tag(name = "findPatient", description = "Find a patient by ID")
    @GetMapping("/patients/{id}")
    public MappingJacksonValue getPatient(@PathVariable int id) {
        Patient patient = patientService.findById(id);

        if (Objects.isNull(patient)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("createdAt", "updatedAt");
        FilterProvider filters = new SimpleFilterProvider().addFilter("patientFilter", filter);
        MappingJacksonValue patientsFilters = new MappingJacksonValue(patient);
        patientsFilters.setFilters(filters);

        return patientsFilters;
    }

    @Tag(name = "create")
    @Tag(name = "createPatient", description = "Create a new patient")
    @PostMapping("/patients")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
        Patient patientAdded = patientService.save(patient);

        if (Objects.isNull(patientAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patientAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Tag(name = "update")
    @Tag(name = "updatePatient", description = "Update an existing patient")
    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @Valid @RequestBody Patient patient) {
        Patient patientAdded = patientService.update(id, patient);

        if (Objects.isNull(patientAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(patientAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
