package com.portfolio.notepad.controller.request.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberLoginForm {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;

    private Boolean idSave;
}
