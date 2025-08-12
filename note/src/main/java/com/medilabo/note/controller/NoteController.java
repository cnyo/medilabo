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

    @GetMapping("/notes")
    public List<Note> getNotes() {
        List<Note> notes = noteService.getNotes();
        log.info("{} notes found", notes.size());

        return notes;
    }

    @GetMapping("/notes/{patientId}")
    public List<Note> getNotes(@PathVariable String patientId) {

        List<Note> notes = noteService.getNoteByPatientId(patientId);
        log.info("Notes for patient {} found", patientId);

        return notes;
    }

    @PostMapping("/notes")
    public ResponseEntity<String> addNote(@RequestBody Note note) {
        log.info("Adding note for patient {}", note.getPatId());
        Note savedNote = noteService.addNote(note);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedNote.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/notes")
    public Note updateNote(@RequestBody Note note) {
        log.info("Updating note with id {}", note.getId());
        return noteService.updateNote(note);
    }
}
