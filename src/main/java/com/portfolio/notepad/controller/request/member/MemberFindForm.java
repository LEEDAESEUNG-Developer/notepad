package com.portfolio.notepad.controller.request.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MemberFindForm {

    @JsonProperty("id")
    private String loginId;
    private String pwd;
}
