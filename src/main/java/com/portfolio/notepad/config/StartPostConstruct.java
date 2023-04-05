package com.portfolio.notepad.config;

import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.entity.MemoType;
import com.portfolio.notepad.entity.Note;
import com.portfolio.notepad.repository.MemberJpaRepository;
import com.portfolio.notepad.repository.NoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class StartPostConstruct {

    private final MemberJpaRepository memberJpaRepository;
    private final NoteJpaRepository noteJpaRepository;

    @PostConstruct
    public void initMemberCreate(){
        Member saveMember = new Member("aaaa", "1234");
        memberJpaRepository.save(saveMember);


        Note saveNote = new Note(saveMember, MemoType.BUSINESS, "제목입니다.", "내용입니다");
        noteJpaRepository.save(saveNote);
    }
}
