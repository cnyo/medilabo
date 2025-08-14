package com.medilabo.assessment.proxies.note;

import com.medilabo.assessment.configuration.FeignAuthPropagationConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "note-service", configuration = FeignAuthPropagationConfig.class)
public interface MicroserviceNoteProxy {

    @GetMapping("/patients/{patientId}/notes")
    List<NoteDto> getNotesByPatientId(@PathVariable("patientId") String id);
}
