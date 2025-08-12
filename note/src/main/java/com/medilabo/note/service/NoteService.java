package com.medilabo.note.service;

import com.medilabo.note.exception.NoteNotFoundException;
import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NoteService.class);

    public Note getNoteById(String id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            log.debug(note.get().toString());
            return note.get();
        } else {
            log.error("Note not found");
            throw new NoteNotFoundException("Note not found with id: " + id);
        }
    }

    public List<Note> getNoteByPatientId(String patientId) {
        List<Note> notes = noteRepository.findByPatId(patientId);
        log.debug("{} notes found", notes.size());

        return notes;
    }

    public List<Note> getNotes() {
        List<Note> note = noteRepository.findAll();
        log.debug("Note count: {}", note.size());

        return note;
    }

    public Note addNote(Note note) {
        log.debug("Adding note: {}", note);
        Note savedNote = noteRepository.insert(note);
        log.info("Note added with id: {}", savedNote.getId());

        return savedNote;
    }

    public Note updateNote(Note note) {
        log.debug("Updating note: {}", note);
        Optional<Note> existingNote = noteRepository.findById(note.getId());
        if (existingNote.isPresent()) {
            Note updatedNote = noteRepository.save(note);
            log.info("Note updated with id: {}", updatedNote.getId());

            return updatedNote;
        } else {
            log.error("Note not found for update");
            throw new NoteNotFoundException("Note not found with id: " + note.getId());
        }
    }
}
