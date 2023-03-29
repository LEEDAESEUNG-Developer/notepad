package com.portfolio.notepad.exception;

public class MemberCreateError extends RuntimeException {

    private static final String MESSAGE = "이 아이디로 가입된 유저가 있습니다.";

    public MemberCreateError() {
        super(MESSAGE);
    }
}
