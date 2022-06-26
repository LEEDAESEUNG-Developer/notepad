package com.portfolio.notepad.note;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NoteEntity {
    private Long noteId;        // 메모 아이디
    private String memberID;    // 멤머 아이디
    private int type;           // 10: Business, 20: Social, 30: Important
    private String title;       // 제목
    private String description; // 내용
    private Timestamp regDate;  // 생성일
}
