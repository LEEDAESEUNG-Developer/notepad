package com.portfolio.notepad.controller.form;

import lombok.Data;

@Data
public class MemberFindPwdForm {
    private String memberId;

    public MemberFindPwdForm(String memberId) {
        this.memberId = memberId;
    }
}
