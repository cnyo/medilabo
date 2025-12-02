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

    public List<Note> getNotesByPatientId(String patientId) {
        List<Note> notes = noteRepository.findByPatId(patientId);
        log.debug("{} notes found", notes.size());

        return notes;
    }

    public Note addNote(Note note, String patientId) {
        log.debug("Adding note: {}", note);
        note.setPatId(patientId);
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
