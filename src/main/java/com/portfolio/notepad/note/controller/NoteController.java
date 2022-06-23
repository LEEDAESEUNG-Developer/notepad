package com.portfolio.notepad.note.controller;

import com.portfolio.notepad.note.NoteEntity;
import com.portfolio.notepad.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NoteController {

    private final NoteService noteService;

    // CRUD
    // 게시판 생성
    @PostMapping("/noteAdd")
    public String noteAdd(@ModelAttribute NoteEntity note, HttpSession httpSession) {
        note.setMemberID((String) httpSession.getAttribute("memberId"));
        note.setRegData(new Timestamp(System.currentTimeMillis()));

        log.info("note => {}", note);
        noteService.addNote(note);

        return "redirect:notes";
    }


    @GetMapping("/notes")
    public String notes(HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("memberId") == null) return "redirect:/login";

        String memberId = (String) httpSession.getAttribute("memberId");
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setMemberID(memberId);

        List<NoteEntity> notes = noteService.getNotes(noteEntity);

        log.info("notes => {}", notes);

        model.addAttribute("notes", notes);
        return "dashboard";
    }

    @GetMapping(value = "/typeupdate", params = {"noteId", "type"})
    public String typeupdate(@ModelAttribute NoteEntity note, HttpSession httpSession) {
        String memberId = (String) httpSession.getAttribute("memberId");
        note.setMemberID(memberId);
//        noteService.
        return "redirect:notes";
    }

    @GetMapping(value = "/deleteNote", params = "noteId")
    public String deleteNote(@ModelAttribute NoteEntity note, HttpSession httpSession) {
        String memberId = (String) httpSession.getAttribute("memberId");
        note.setMemberID(memberId);
        noteService.deleteNote(note);

        return "redirect:notes";
    }
}
