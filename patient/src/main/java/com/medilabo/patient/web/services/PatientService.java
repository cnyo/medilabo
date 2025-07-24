package com.medilabo.patient.web.services;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.web.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(int id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient save(Patient patient) {
        if (patient.getId() != 0 && patientRepository.existsById(patient.getId())) {
            throw new RuntimeException("Patient already exists with id: " + patient.getId());
        }

        return patientRepository.save(patient);
    }

    public Patient update(int id, Patient patient) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + patient.getId());
        }

        Patient existingPatient = findById(id);
        existingPatient.setName(patient.getName());
        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setBirthDate(patient.getBirthDate());
        existingPatient.setGender(patient.getGender());
        existingPatient.setAddress(patient.getAddress());
        existingPatient.setPhoneNumber(patient.getPhoneNumber());

        return patientRepository.save(existingPatient);
    }
}
