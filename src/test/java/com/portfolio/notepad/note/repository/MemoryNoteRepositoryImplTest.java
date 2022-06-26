package com.portfolio.notepad.note.repository;

import com.portfolio.notepad.note.NoteEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

class MemoryNoteRepositoryImplTest {

    private final MemoryNoteRepositoryImpl memoryNoteRepository = new MemoryNoteRepositoryImpl();

    @BeforeEach
    void beforeEach(){
        NoteEntity noteEntity02 = new NoteEntity();
        noteEntity02.setNoteId(2L);
        noteEntity02.setMemberID("userA");
        noteEntity02.setTitle("제목: 테스트(userA)");
        noteEntity02.setDescription("내용: 테스트");
        noteEntity02.setRegDate(new Timestamp(System.currentTimeMillis()));

        NoteEntity noteEntity03 = new NoteEntity();
        noteEntity03.setNoteId(3L);
        noteEntity03.setMemberID("userA");
        noteEntity03.setTitle("제목: 테스트(userA)");
        noteEntity03.setDescription("내용: 테스트");
        noteEntity03.setRegDate(new Timestamp(System.currentTimeMillis()));

        NoteEntity noteEntity04 = new NoteEntity();
        noteEntity04.setNoteId(4L);
        noteEntity04.setMemberID("userB");
        noteEntity04.setTitle("제목: 테스트(userB)");
        noteEntity04.setDescription("내용: 테스트");
        noteEntity04.setRegDate(new Timestamp(System.currentTimeMillis()));

        memoryNoteRepository.addNote(noteEntity02);
        memoryNoteRepository.addNote(noteEntity03);
        memoryNoteRepository.addNote(noteEntity04);
    }

    @AfterEach
    void afterEach(){
        memoryNoteRepository.deleteAll();
    }

    @Test
    @DisplayName("노트 추가 성공")
    void noteAdd_O() {
        // given
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setNoteId(1L);
        noteEntity.setMemberID("userA");
        noteEntity.setTitle("제목: 테스트");
        noteEntity.setDescription("내용: 테스트");
        noteEntity.setRegDate(new Timestamp(System.currentTimeMillis()));

        // when
        memoryNoteRepository.addNote(noteEntity);

        // then
        NoteEntity findNote = memoryNoteRepository.findNote(noteEntity);

        Assertions.assertThat(findNote).isEqualTo(noteEntity);
    }

    @Test
    @DisplayName("멤버 아이디를 사용하여 메모를 모두 가지고 온다")
    void getNotes_O(){
        // given 이렇게 주어졌을 때
        NoteEntity noteEntity02 = new NoteEntity();
        noteEntity02.setNoteId(2L);
        NoteEntity noteEntity03 = new NoteEntity();
        noteEntity03.setNoteId(3L);

        NoteEntity findnote02 = memoryNoteRepository.findNote(noteEntity02);
        NoteEntity findnote03 = memoryNoteRepository.findNote(noteEntity03);

        // 멤버 userA 객체를 가져올 때 사용되는 객체
        NoteEntity findUserNoteEntity = new NoteEntity();
        findUserNoteEntity.setMemberID("userA");


        // when 했을 때
        List<NoteEntity> findNote = memoryNoteRepository.getNotes(findUserNoteEntity);

        // then 처리된 결과
        Assertions.assertThat(findNote).contains(findnote02, findnote03);
    }
}