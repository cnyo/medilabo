package com.medilabo.front.controller;

import com.medilabo.front.beans.PatientBean;
import com.medilabo.front.proxies.MicroservicePatientsProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FrontController {
    private final MicroservicePatientsProxy patientsProxy;

    public FrontController(MicroservicePatientsProxy patientsProxy) {
        this.patientsProxy = patientsProxy;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<PatientBean> patients = patientsProxy.getPatients();
        model.addAttribute("patients", patients);

        return "index";
    }

    @GetMapping("/detail-patient/{id}")
    public String fichePatient(@PathVariable int id, Model model) {
        PatientBean patient = patientsProxy.getPatient(id);
        model.addAttribute("patient", patient);

        return "fichePatient";
    }

    @PostMapping(value="/update-patient/{id}")
    public String updatePatient(@PathVariable int id, PatientBean updatedPatient) {
        System.out.println(updatedPatient);
        ResponseEntity<PatientBean> patient = patientsProxy.updatePatient(id, updatedPatient);

        return "redirect:/detail-patient/" + id;
    }
}
