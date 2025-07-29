package com.medilabo.front.proxies;

import com.medilabo.front.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-patients", url = "localhost:9002")
public interface MicroservicePatientsProxy {
    @GetMapping("/patients")
    List<PatientBean> getPatients();

    @GetMapping("/patients/{id}")
    PatientBean getPatient(@PathVariable("id") int id);

    @PutMapping("/patients/{id}")
    ResponseEntity<PatientBean> updatePatient(@PathVariable("id") int id, @RequestBody PatientBean patient);
}
