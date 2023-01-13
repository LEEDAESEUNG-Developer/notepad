package com.portfolio.notepad.controller.form;

import com.portfolio.notepad.entity.MemoType;
import lombok.Data;

@Data
public class NoteUpdateForm {
    private MemoType memoType;
    private String title;
    private String description;

    public NoteUpdateForm(MemoType memoType, String title, String description) {
        this.memoType = memoType;
        this.title = title;
        this.description = description;
    }
}
