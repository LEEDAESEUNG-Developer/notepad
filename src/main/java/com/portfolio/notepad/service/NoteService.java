package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.form.NoteCreateForm;
import com.portfolio.notepad.controller.form.NoteUpdateForm;
import com.portfolio.notepad.entity.Note;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NoteService {

    /**
     * 메모 추가
     *
     * @return
     */
    @Transactional
    public Note addNote(NoteCreateForm form);

    /**
     * 메모 하나 가지고 오기
     * @return
     */
    public Note getNote(Long noteId);

    /**
     * 회원 아이디로 노트를 가지고 온다.
     * @param memberId 객체에 회원 아이디
     * @return 회원 아이디에 메모를 모두 가지고온다.
     */
    public List<Note> getNotes(Long memberId);

    /**
     * 메모를 수정함
     */
    @Transactional
    public void editNote(Long noteId, NoteUpdateForm form);

    /**
     * 메모 삭제
     */
    @Transactional
    public void deleteNote(Long noteId);

}
