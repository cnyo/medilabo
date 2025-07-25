package com.medilabo.front.controller;

import com.medilabo.front.beans.PatientBean;
import com.medilabo.front.proxies.MicroservicePatientsProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ClientController {
    private final MicroservicePatientsProxy patientsProxy;

    public ClientController(MicroservicePatientsProxy patientsProxy) {
        this.patientsProxy = patientsProxy;
    }

    @RequestMapping("/")
    public String index(Model model) {
        List<PatientBean> patients = patientsProxy.getPatients();
        model.addAttribute("patients", patients);

        return "index";
    }

    @RequestMapping("/detail-patient/{id}")
    public String fichePatient(@PathVariable int id, Model model) {
        PatientBean patient = patientsProxy.getPatient(id);
        model.addAttribute("patient", patient);

        return "fichePatient";
    }
}
