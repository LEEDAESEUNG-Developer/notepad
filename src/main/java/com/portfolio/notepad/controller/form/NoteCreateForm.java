package com.portfolio.notepad.controller.form;

import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.entity.MemoType;
import lombok.Data;

@Data
public class NoteCreateForm {
    private Long memberId;
    private MemoType memoType;
    private String title;
    private String description;

    public NoteCreateForm() {
    }

    public NoteCreateForm(Long memberId, MemoType memoType, String title, String description) {
        this.memberId = memberId;
        this.memoType = memoType;
        this.title = title;
        this.description = description;
    }
}
