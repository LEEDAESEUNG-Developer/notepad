package com.portfolio.notepad.exception;

/**
 * 사용자를 찾을수 없을 경우 예외처리
 */
public class MemberNotFount extends RuntimeException {

    private static final String MESSAGE = "이 회원은 없는 회원 입니다.";

    public MemberNotFount() {
        super(MESSAGE);
    }


}
