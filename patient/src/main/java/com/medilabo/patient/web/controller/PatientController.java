package com.medilabo.patient.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.web.exceptions.PatientNotFoundException;
import com.medilabo.patient.web.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public MappingJacksonValue getPatientList() {
        List<Patient> patients = patientService.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("createdAt", "updatedAt", "id");
        FilterProvider filters = new SimpleFilterProvider().addFilter("patientFilter", filter);
        MappingJacksonValue patientsFilters = new MappingJacksonValue(patients);
        patientsFilters.setFilters(filters);

        return patientsFilters;
    }

    @GetMapping("/patients/{id}")
    public MappingJacksonValue getPatient(@PathVariable int id) {
        Patient patient = patientService.findById(id);

        if (Objects.isNull(patient)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("createdAt", "updatedAt", "id");
        FilterProvider filters = new SimpleFilterProvider().addFilter("patientFilter", filter);
        MappingJacksonValue patientsFilters = new MappingJacksonValue(patient);
        patientsFilters.setFilters(filters);

        return patientsFilters;
    }

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

    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @Valid @RequestBody Patient patient) {
        Patient patientAdded = patientService.update(id, patient);

        if (Objects.isNull(patientAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(patientAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
