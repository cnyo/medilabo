package com.medilabo.assessment.proxies.patient;

import com.medilabo.assessment.configuration.FeignAuthPropagationConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface to communicate with the Patient microservice.
 * It defines methods to retrieve patient details by their ID.
 */
@FeignClient(name = "patient-service", url = "http://patient:9002", configuration = FeignAuthPropagationConfig.class)
public interface MicroservicePatientProxy {

    /**
     * Retrieves patient details for a given patient ID.
     *
     * @param id The ID of the patient to be retrieved.
     * @return A PatientDto object representing the patient's details.
     */
    @GetMapping("/patients/{id}")
    PatientDto getPatientById(@PathVariable("id") int id);
}
