package com.portfolio.notepad.controller.request.member;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class MemberFindPwdForm {

    @NotEmpty(message = "아이디가 공백 입니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호가 공백 입니다.")
    private String changePassword;

    public MemberFindPwdForm(String loginId, String changePassword) {
        this.loginId = loginId;
        this.changePassword = changePassword;
    }
}
