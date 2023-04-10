package com.portfolio.notepad.controller;

import com.portfolio.notepad.controller.form.NoteCreateForm;
import com.portfolio.notepad.controller.form.NoteUpdateForm;
import com.portfolio.notepad.controller.session.MemberSession;
import com.portfolio.notepad.entity.MemoType;
import com.portfolio.notepad.entity.Note;
import com.portfolio.notepad.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NoteController {

    private final NoteService noteService;

    /**
     * 게시판 생성
     * @param form 노트객체
     */
    @PostMapping("/noteAdd")
    public String noteAdd(@Valid @ModelAttribute("noteCreateForm") NoteCreateForm form, BindingResult bindingResult, @SessionAttribute("member") MemberSession member) {

        if(bindingResult.hasErrors()){ // form 값 검증
            return "addNoteError";
        }

        form.setMemberId(member.getId()); // 바꺼보는걸로
        log.debug("/noteAdd -> note => {}", form);
        noteService.addNote(form);

        return "redirect:notes";
    }

    /**
     * 회원 메모들을 가지고 오기
     */
    @GetMapping("/notes")
    public String notes(@SessionAttribute(name = "member", required = false) MemberSession member, Model model) {
        if (member == null) return "redirect:/";

        Long memberId = member.getId();

        List<Note> notes = noteService.getNotes(memberId);

        model.addAttribute("noteCreateForm", new NoteCreateForm());
        model.addAttribute("notes", notes);
        return "dashboard";
    }

    /**
     * 회원이 메모 Business, Social, Important 클릭했을 때 바꾸기
     *
     * @param noteId 노트객체
     * @param type
     */
    @GetMapping(value = "/typeupdate", params = {"noteId", "type"})
    public String typeupdate(@RequestParam("noteId") Long noteId, @RequestParam String type, @SessionAttribute("member") MemberSession member) {
        Note findNote = noteService.getNote(noteId);
        NoteUpdateForm form = new NoteUpdateForm(MemoType.valueOf(type), findNote.getTitle(), findNote.getDescription());
        noteService.editNote(noteId, form);
        return "redirect:notes";
    }

    /**
     * 메모 아이디를 가지고 삭제
     * @param noteId 메모객체(메모 아이디)
     */
    @GetMapping(value = "/deleteNote", params = "noteId")
    public String deleteNote(@RequestParam Long noteId, @SessionAttribute("member") MemberSession member) {
        if(member == null ) return "redirect:/";

        noteService.deleteNote(noteId);

        return "redirect:notes";
    }
}
