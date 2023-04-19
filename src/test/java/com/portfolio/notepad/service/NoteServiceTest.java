package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.request.member.MemberCreateForm;
import com.portfolio.notepad.controller.request.note.NoteCreateForm;
import com.portfolio.notepad.controller.request.note.NoteUpdateForm;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.entity.MemoType;
import com.portfolio.notepad.entity.Note;
import com.portfolio.notepad.exception.MemberNotFount;
import com.portfolio.notepad.exception.NoteNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NoteServiceTest {

    @Autowired
    private NoteService noteService;
    @Autowired
    private MemberService memberService;
    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void beforeEach(){
        memberService.register(new MemberCreateForm("memberA", "1234"));
    }

    @Test
    void 메모추가_성공(){
        //given
        String title = "제목";
        String description = "내용";
        Member findMember = memberService.login("memberA", "1234");

        // when
        Note createNote = saveNote(title, description, findMember, MemoType.BUSINESS);

        em.flush();
        em.clear();

        List<Note> findNotes = noteService.getNotes(findMember.getId());

        //then

        //회원
        assertThat(createNote.getMember().getId()).isEqualTo(findMember.getId());

        //노트
        assertThat(createNote.getTitle()).isEqualTo(title);
        assertThat(createNote.getDescription()).isEqualTo(description);
        assertThat(findNotes.size()).isEqualTo(1);
    }

    // 비회원이 메모를 추가할 경우
    @Test
    void 메모추가_실패(){
        assertThatThrownBy(() -> {
            noteService.addNote(null, new NoteCreateForm(MemoType.BUSINESS, "타이틀", "내용"));
        }).isInstanceOf(MemberNotFount.class);
    }

    @Test
    void 메모수정_성공(){
        //given
        String title = "제목";
        String description = "내용";
        String editTitle = "제목수정";
        Member findMember = memberService.login("memberA", "1234");

        // when
        Note saveNote = saveNote(title, description, findMember, MemoType.BUSINESS);
        saveNote.updateNote(new NoteUpdateForm(saveNote.getMemoType(), editTitle, saveNote.getDescription()));

        em.flush();
        em.clear();

        List<Note> findNotes = noteService.getNotes(findMember.getId());

        //then
        assertThat(findNotes.get(0).getTitle()).isEqualTo(editTitle);
    }

    @Test
    void 메모수정_실패(){
        //given
        String title = "제목";
        String description = "내용";
        String editTitle = "제목수정";
        Member findMember = memberService.login("memberA", "1234");

        // when
        Note saveNote = saveNote(title, description, findMember, MemoType.BUSINESS);

        assertThatThrownBy(() -> {
            noteService.editNote(1000L, new NoteUpdateForm(MemoType.BUSINESS, "제목", "내용"));

        }).isInstanceOf(NoteNotFound.class);

    }

    private Note saveNote(String title, String description, Member member, MemoType memoType) {
        return noteService.addNote(member.getId(), new NoteCreateForm(memoType, title, description));
    }
}