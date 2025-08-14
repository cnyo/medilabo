package com.medilabo.assessment.proxies.patient;

import com.medilabo.assessment.configuration.FeignAuthPropagationConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "patient-service", configuration = FeignAuthPropagationConfig.class)
public interface MicroservicePatientProxy {

    @GetMapping("/patients/{id}")
    PatientDto getPatientById(@PathVariable("id") int id);
}
