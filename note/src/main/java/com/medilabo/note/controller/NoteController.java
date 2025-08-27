package com.medilabo.note.controller;

import com.medilabo.note.model.Note;
import com.medilabo.note.service.NoteService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NoteService.class);

    @GetMapping("/patients/{patientId}/notes")
    public List<Note> getNotes(@PathVariable String patientId) {

        List<Note> notes = noteService.getNotesByPatientId(patientId);
        log.info("Notes for patient {} found: {}", patientId, notes.size());

        return notes;
    }

    @PostMapping("/patients/{patientId}/notes")
    public ResponseEntity<String> addNote(@RequestBody Note note, @PathVariable String patientId) {
        Note savedNote = noteService.addNote(note, patientId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedNote.getId())
                .toUri();
        log.info("Note added to patient {}", note.getPatId());

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/patients/{patientId}/notes/{id}")
    public Note updateNote(@RequestBody Note note, @PathVariable String patientId, @PathVariable String id) {
        log.info("Updating note with id {}", note.getId());
        return noteService.updateNote(note);
    }
}
