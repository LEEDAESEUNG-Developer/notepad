package com.portfolio.notepad.controller.session;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSession {

    private final Long id;
    private final String memberId;

    @Builder
    public MemberSession(Long id, String memberId) {
        this.id = id;
        this.memberId = memberId;
    }
}
