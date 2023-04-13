package com.portfolio.notepad.controller.form.member;

import lombok.Data;

@Data
public class MemberCreateForm {
    private String memberId;
    private String memberPwd;

    public MemberCreateForm(String memberId, String memberPwd) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
    }
}
