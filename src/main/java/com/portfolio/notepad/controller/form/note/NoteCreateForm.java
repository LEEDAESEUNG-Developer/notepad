package com.portfolio.notepad.controller.form.note;

import com.portfolio.notepad.entity.MemoType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NoteCreateForm {

    private Long memberId;

    private MemoType memoType;

    @NotBlank(message = "공백으로 둘 수 없습니다.")
    private String title;

    @NotBlank(message = "공백으로 둘 수 없습니다.")
    private String description;

    public NoteCreateForm() {
    }

    @Builder
    public NoteCreateForm(Long memberId, MemoType memoType, String title, String description) {
        this.memberId = memberId;
        this.memoType = memoType;
        this.title = title;
        this.description = description;
    }
}
