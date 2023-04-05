package com.portfolio.notepad.controller.session;

import com.portfolio.notepad.entity.Member;
import lombok.Getter;

@Getter
public class MemberSession {

    private final Long id;
    private final String loginId;

    public MemberSession(Member member){
        this.id = member.getId();
        this.loginId = member.getLoginId();
    }
}
