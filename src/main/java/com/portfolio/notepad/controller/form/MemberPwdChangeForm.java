package com.portfolio.notepad.controller.form;

import lombok.Data;

@Data
public class MemberPwdChangeForm {
    private Long memberId;
    private String pwd;

    public MemberPwdChangeForm(Long memberId, String pwd) {
        this.memberId = memberId;
        this.pwd = pwd;
    }
}
