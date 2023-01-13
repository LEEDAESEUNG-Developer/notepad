package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.form.NoteCreateForm;
import com.portfolio.notepad.controller.form.NoteUpdateForm;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.entity.Note;
import com.portfolio.notepad.repository.MemberJpaRepository;
import com.portfolio.notepad.repository.NoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService{

    private final NoteJpaRepository noteJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    @Transactional
    public Note addNote(NoteCreateForm form) {
        Member findMember = memberJpaRepository.findById(form.getMemberId())
                .orElseThrow(() -> new IllegalStateException("없는유저입니다"));
        return noteJpaRepository.save(new Note(findMember, form.getMemoType(), form.getTitle(), form.getDescription()));
    }

    @Override
    public Note getNote(Long noteId) {
        return noteJpaRepository.findById(noteId)
                .orElseThrow(() -> new IllegalStateException("노트가 없습니다."));
    }

    @Override
    public List<Note> getNotes(Long memberId) {
        return noteJpaRepository.findByMemberId(memberId);
    }

    @Override
    public void editNote(Long noteId, NoteUpdateForm form) {
        Note findNote = noteJpaRepository.findById(noteId)
                .orElseThrow(() -> new IllegalStateException("노트가 없습니다."));

        findNote.updateNote(form);
    }

    @Override
    @Transactional
    public void deleteNote(Long noteId) {
        Note findNote = noteJpaRepository.findById(noteId)
                .orElseThrow(() -> new IllegalStateException("노트가 없습니다."));
        noteJpaRepository.delete(findNote);
    }
}
