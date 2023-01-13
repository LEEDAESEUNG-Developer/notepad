package com.portfolio.notepad.repository;

import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.entity.Note;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Primary
public interface NoteJpaRepository extends JpaRepository<Note, Long> {

    List<Note> findByMemberId(Long memberId);
}
