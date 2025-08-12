package com.medilabo.note.controller;

import com.medilabo.note.exception.NoteNotFoundException;
import com.medilabo.note.model.Note;
import com.medilabo.note.service.NoteService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
