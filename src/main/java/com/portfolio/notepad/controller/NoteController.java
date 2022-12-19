package com.portfolio.notepad.controller;

import com.portfolio.notepad.domain.NoteEntity;
import com.portfolio.notepad.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NoteController {

    private final NoteService noteService;

    // CRUD

    /**
     * 게시판 생성
     * @param note 노트객체
     */
    @PostMapping("/noteAdd")
    public String noteAdd(@ModelAttribute NoteEntity note, HttpSession httpSession) {
        note.setMemberID((String) httpSession.getAttribute("memberId"));

        log.info("/noteAdd -> note => {}", note);
        noteService.addNote(note);

        return "redirect:notes";
    }

    /**
     * 회원 메모들을 가지고 오기
     */
    @GetMapping("/notes")
    public String notes(HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("memberId") == null) return "redirect:/member/login";

        String memberId = (String) httpSession.getAttribute("memberId");
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setMemberID(memberId);

        List<NoteEntity> notes = noteService.getNotes(noteEntity);

        log.info("/notes -> notes => {}", notes);

        model.addAttribute("notes", notes);
        return "dashboard";
    }

    /**
     * 회원이 메모 Business, Social, Important 클릭했을 때 바꾸기
     * @param note 노트객체
     */
    @GetMapping(value = "/typeupdate", params = {"noteId", "type"})
    public String typeupdate(@ModelAttribute NoteEntity note, HttpSession httpSession) {
        String memberId = (String) httpSession.getAttribute("memberId");
        note.setMemberID(memberId);
        log.info("/typeupdate -> note = {}", note);
        noteService.updateype(note);
        return "redirect:notes";
    }

    /**
     * 메모 아이디를 가지고 삭제
     * @param note 메모객체(메모 아이디)
     */
    @GetMapping(value = "/deleteNote", params = "noteId")
    public String deleteNote(@ModelAttribute NoteEntity note, HttpSession httpSession) {
        String memberId = (String) httpSession.getAttribute("memberId");
        note.setMemberID(memberId);
        noteService.deleteNote(note);

        return "redirect:notes";
    }
}
