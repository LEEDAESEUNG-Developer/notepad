package com.portfolio.notepad.login.controller;

import com.portfolio.notepad.login.MemberEntity;
import com.portfolio.notepad.login.repository.MemberRepository;
import com.portfolio.notepad.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @RequestMapping("/login")
    public String login(@ModelAttribute MemberEntity member, HttpSession httpSession) {
        if (memberService.isLogin(member)) {
            httpSession.setAttribute("memberId", member.getId());
            return "redirect:notes";
        } else {
            return "redirect:index.html";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:index.html";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute MemberEntity memberEntity) {
        memberService.register(memberEntity);
        return "redirect:index.html";
    }

}
