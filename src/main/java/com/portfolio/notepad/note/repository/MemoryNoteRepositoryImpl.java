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
    public void updatetype(NoteEntity note) {
        // 1. 노트아이디를 검색해서 메모를 가지고 온다.
        // 2. 매개변수 note memberId와 검색한 메모 memberId와 비교
        // 3. 일치할 경우 note 객체 프로퍼티를 넣어서 저장소에 값을 비교한다.
        NoteEntity findNote = findNote(note);

        if (note.getMemberID().equalsIgnoreCase(findNote.getMemberID())) {
            repository.remove(note.getNoteId());
            findNote.setType(note.getType());
            repository.put(findNote.getNoteId(), findNote);
        }
    }

    @Override
    public void deleteNote(NoteEntity note) {
        repository.remove(note.getNoteId());
    }

    public void deleteAll(){
        repository.clear();
    }
}
