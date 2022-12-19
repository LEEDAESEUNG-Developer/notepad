package com.portfolio.notepad.service;

import com.portfolio.notepad.domain.NoteEntity;
import com.portfolio.notepad.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public void addNote(NoteEntity note) {
        noteRepository.addNote(note);
    }

    @Override
    public NoteEntity getNote(NoteEntity note) {
        return noteRepository.findNote(note);
    }

    @Override
    public List<NoteEntity> getNotes(NoteEntity note) {
        return noteRepository.getNotes(note);
    }

    @Override
    public void updateype(NoteEntity note) {
        noteRepository.updatetype(note);
    }

    @Override
    public void deleteNote(NoteEntity note) {
        noteRepository.deleteNote(note);
    }
}
