package com.portfolio.notepad.repository;

import com.portfolio.notepad.domain.NoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
@RequiredArgsConstructor
public class DatabaseNoteRepositoryImpl implements NoteRepository{

    private final NoteMapper noteMapper;
    
    @Override
    public void addNote(NoteEntity note) {
        noteMapper.addUserNote(note);
    }

    @Override
    public NoteEntity findNote(NoteEntity note) {
        return null;
    }

    @Override
    public List<NoteEntity> getNotes(NoteEntity note) {
        return noteMapper.getUserNotes(note);
    }

    @Override
    public void updatetype(NoteEntity note) {
        noteMapper.setUpdateType(note);
    }

    @Override
    public void deleteNote(NoteEntity note) {
        noteMapper.deleteUserNote(note);
    }
}
