package com.portfolio.notepad.exception;

public class NoteNotFound extends RuntimeException {
    private static final String MESSAGE = "없는 메모 입니다.";

    public NoteNotFound() {
        super(MESSAGE);
    }
}
