package com.medilabo.front.proxies;

import com.medilabo.front.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservice-patients", url = "localhost:9090")
public interface MicroservicePatientsProxy {
    @GetMapping("/patients")
    List<PatientBean> getPatients();

    @GetMapping("/patients/{id}")
    PatientBean getPatient(@PathVariable("id") int id);
}
