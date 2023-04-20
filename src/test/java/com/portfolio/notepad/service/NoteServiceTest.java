package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.request.note.NoteCreateForm;
import com.portfolio.notepad.controller.request.note.NoteUpdateForm;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.entity.MemoType;
import com.portfolio.notepad.entity.Note;
import com.portfolio.notepad.exception.MemberNotFount;
import com.portfolio.notepad.exception.NoteNotFound;
import com.portfolio.notepad.repository.MemberJpaRepository;
import com.portfolio.notepad.repository.NoteJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class NoteServiceTest {

    private static final String LOGIN_ID = "memberA";
    private static final String PASSWORD = "1234";

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteJpaRepository noteJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @BeforeEach
    void beforeEach(){
        noteJpaRepository.deleteAll(); //PostConstruct가 note 만들어줘서 테스트가 제대로 되지 않기에 삭제함
        memberJpaRepository.save(new Member(LOGIN_ID, PASSWORD));
    }

    @AfterEach
    void afterEach(){
        memberJpaRepository.deleteAll();
    }

    @DisplayName("메모추가 되어야함")
    @Test
    void 메모추가_성공(){
        //given
        String title = "제목";
        String description = "내용";
        Member findMember = getMember(LOGIN_ID, PASSWORD);

        // when
        Note createNote = saveNote(title, description, findMember, MemoType.BUSINESS);

        List<Note> findNotes = noteService.getNotes(findMember.getId());

        //then

        //회원
        assertThat(createNote.getMember().getId()).isEqualTo(findMember.getId());

        //노트
        assertThat(createNote.getTitle()).isEqualTo(title);
        assertThat(createNote.getDescription()).isEqualTo(description);
        assertThat(findNotes.size()).isEqualTo(1);
    }

    @DisplayName("비회원이 메모를 추가할 때 예외처리가 되어야함")
    @Test
    void 메모추가_실패(){
        assertThatThrownBy(() -> {
            noteService.addNote(0L, new NoteCreateForm(MemoType.BUSINESS, "타이틀", "내용"));
        }).isInstanceOf(MemberNotFount.class);
    }

    @DisplayName("메모 수정이 되어야함")
    @Test
    void 메모수정_성공(){
        //given
        String title = "제목";
        String description = "내용";
        String editTitle = "제목수정";
        Member findMember = getMember(LOGIN_ID, PASSWORD);

        // when
        Note saveNote = saveNote(title, description, findMember, MemoType.BUSINESS);
        saveNote.updateNote(new NoteUpdateForm(saveNote.getMemoType(), editTitle, saveNote.getDescription()));

        List<Note> findNotes = noteService.getNotes(findMember.getId());

        //then
        assertThat(findNotes.get(0).getTitle()).isEqualTo(editTitle);
    }

    @DisplayName("없는 메모를 수정하게 될 경우 예외처리가 되어야함")
    @Test
    void 메모수정_실패(){
        //given
        String editTitle = "제목수정";
        String editContent = "내용수정";

        // then
        assertThatThrownBy(() -> {
            noteService.editNote(1000L, new NoteUpdateForm(MemoType.BUSINESS,
                    editTitle,
                    editContent));

        }).isInstanceOf(NoteNotFound.class);

    }

    private Member getMember(String memberId, String password){
        return memberJpaRepository.findByLoginIdAndPwd(memberId, password)
                .orElseThrow(MemberNotFount::new);
    }

    private Note saveNote(String title, String description, Member member, MemoType memoType) {
        return noteService.addNote(member.getId(), new NoteCreateForm(memoType, title, description));
    }
}