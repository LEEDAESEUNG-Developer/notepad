package com.portfolio.notepad.note.repository;

import com.portfolio.notepad.note.NoteEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryNoteRepositoryImpl implements NoteRepository {

    private final static Map<Long, NoteEntity> repository = new HashMap<>();
    private long seq;

    @Override
    public void addNote(NoteEntity note) {
        note.setNoteId(seq++);
        repository.put(note.getNoteId(), note);
    }

    @Override
    public NoteEntity findNote(NoteEntity note) {
        return repository.get(note.getNoteId());
    }

    // 멤버 아이디 -> 그 멤버아이디 노트를 모두 가지고 온다.
    @Override
    public List<NoteEntity> getNotes(NoteEntity note) {
        List<NoteEntity> noteEntities = new ArrayList<>();

        for (Long key : repository.keySet()) {
            if (key != null) {
                NoteEntity findNoteEntity = repository.get(key);
                if (findNoteEntity.getMemberID().equals(note.getMemberID()))
                    noteEntities.add(findNoteEntity);
            }
        }
        return noteEntities;
    }

    @Override
    public void deleteNote(NoteEntity note) {
        repository.remove(note.getNoteId());
    }

    public void deleteAll(){
        repository.clear();
    }
}
