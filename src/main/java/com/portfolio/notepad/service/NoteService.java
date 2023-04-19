package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.request.note.NoteCreateForm;
import com.portfolio.notepad.controller.request.note.NoteUpdateForm;
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
    Note addNote(Long memberId, NoteCreateForm form);

    /**
     * 메모 하나 가지고 오기
     * @return
     */
    Note getNote(Long noteId);

    /**
     * 회원 아이디로 노트를 가지고 온다.
     * @param memberId 객체에 회원 아이디
     * @return 회원 아이디에 메모를 모두 가지고온다.
     */
    List<Note> getNotes(Long memberId);

    /**
     * 메모를 수정함
     */
    @Transactional
    void editNote(Long noteId, NoteUpdateForm form);

    /**
     * 메모 삭제
     */
    @Transactional
    void deleteNote(Long noteId);

}
