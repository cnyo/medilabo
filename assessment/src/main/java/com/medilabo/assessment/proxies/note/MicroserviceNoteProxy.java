package com.medilabo.assessment.proxies.note;

import com.medilabo.assessment.configuration.FeignAuthPropagationConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Feign client interface to communicate with the Note microservice.
 * It defines methods to retrieve notes associated with a specific patient.
 */
@FeignClient(name = "note-service", url = "http://note:9005",configuration = FeignAuthPropagationConfig.class)
public interface MicroserviceNoteProxy {

    /**
     * Retrieves a list of notes for a given patient ID.
     *
     * @param id The ID of the patient whose notes are to be retrieved.
     * @return A list of NoteDto objects representing the patient's notes.
     */
    @GetMapping("/patients/{patientId}/notes")
    List<NoteDto> getNotesByPatientId(@PathVariable("patientId") String id);
}
