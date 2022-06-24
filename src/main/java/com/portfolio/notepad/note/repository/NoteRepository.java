package com.portfolio.notepad.note.repository;

import com.portfolio.notepad.note.NoteEntity;

import java.util.List;

public interface NoteRepository {

    /**
     * 노트를 추가한다
     * @param note 추가할 노트
     */
    public void addNote(NoteEntity note);

    /**
     * 하나의 노트를 가지고 온다
     * @param note 노트객체에 있는 노트 번호
     * @return  노트를 리턴
     */
    public NoteEntity findNote(NoteEntity note);

    /**
     * 회원 노트를 모두 가지고온다
     * @param note 노트 객체에 있는 회원 아이디로 노트를 가지고 온다
     * @return 회원 아이디에 등록된 노트 객체를 모두 반환
     */
    public List<NoteEntity> getNotes(NoteEntity note);

    /**
     * 노트 타입을 변경한다
     * Business, Social, Important
     * @param note
     */
    public void updatetype(NoteEntity note);

    /**
     * 노트를 삭제
     * @param note 노트 객체에 있는 노트번호를 가지고 삭제한다
     */
    public void deleteNote(NoteEntity note);

}
