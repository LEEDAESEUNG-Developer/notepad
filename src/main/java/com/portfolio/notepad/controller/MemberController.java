package com.portfolio.notepad.controller;

import com.portfolio.notepad.controller.form.MemberCreateForm;
import com.portfolio.notepad.controller.form.MemberPwdChangeForm;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(@ModelAttribute(name = "form") MemberCreateForm form){
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute MemberCreateForm form) {
        memberService.register(form);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String pwd, HttpSession session) {

        if(session.getAttribute("member") != null) return "redirect:/notes";

        Member member = memberService.login(id, pwd);
        session.setAttribute("member", member);
        return "redirect:/notes";
    }
/*
    @ResponseBody
    @PostMapping("/findPassword")
    public String findPassword(@RequestBody MemberEntity member) {
        JsonObject jsonObject = new JsonObject();
        MemberEntity findMember = memberService.findMember();

        log.info("member args = {}", member);
        log.info("findMember test = {}", memberService.findMember(member));

        if (member.getId().equals(findMember.getId()) && member.getPwd().equals(findMember.getPwd())) {
            jsonObject.addProperty("status", "ok");
        } else {
            jsonObject.addProperty("status", "bad");
        }
        return new Gson().toJson(jsonObject);
    }*/

    @PostMapping("/change/password")
    public String passwordChange(
            @SessionAttribute(name = "member") Member sessionMember,
            @ModelAttribute MemberPwdChangeForm form){

        form.setMemberId(sessionMember.getId());
        memberService.changeMemberPwd(form);
        
        return "redirect:/notes";
    }

    @PostMapping("/delete")
    public String delete(@SessionAttribute("member") Member member) {
//        if(memberId == null) return login(member, httpSession); // 회원 없는 사람이 비밀번호 변경 방지

        memberService.deleteMember(member.getId());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }


}
