package com.portfolio.notepad.controller.request.member;

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
