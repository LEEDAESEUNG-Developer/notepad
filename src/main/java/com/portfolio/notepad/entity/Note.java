package com.portfolio.notepad.entity;

import com.portfolio.notepad.controller.request.note.NoteUpdateForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MemoType memoType;
    private String title;
    private String description;

    public Note(Member member, MemoType memoType, String title, String description) {
        this.member = member;
        this.memoType = memoType;
        this.title = title;
        this.description = description;
    }

    //==연관관계 메소드==//
    public void setMember(Member member){
        this.member = member;
        member.getNotes().add(this);
    }

    //==비즈니스 로직 ==//
    public void updateNote(NoteUpdateForm form){
        memoType = form.getMemoType();
        title = form.getTitle();
        description = form.getDescription();
    }
}
