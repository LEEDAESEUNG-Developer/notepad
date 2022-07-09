package com.portfolio.notepad.login.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.portfolio.notepad.login.MemberEntity;
import com.portfolio.notepad.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping("/member")
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

    /**
     * < 회원 탈퇴 > </>
     * 결과물: 회원 정보 변경 -> 비밀번호 -> ajax를 사용하여 컨트롤러와 비밀번호를 일치하는지 확인
     * <p>
     * 아이디를 받아와서 db에 아이디 조회를해서 비밀번호를 가지고 온다. (스프링)
     * dashboard.html에 비밀번호와 대조를 해본다. (일치 T, 불일치 F) (프론트)
     * dashboard.html -> 리턴값을 가지고 탈퇴 여부를 결정한다. (프론트)
     * db에 회원삭제, 노트삭제 (db)
     */
    @ResponseBody
    @PostMapping("/findPassword")
    public String findPassword(@RequestBody MemberEntity member) {
        JsonObject jsonObject = new JsonObject();
        MemberEntity findMember = memberService.findMember(member);

        log.info("member args = {}", member);
        log.info("findMember test = {}", memberService.findMember(member));

        if (member.getId().equals(findMember.getId()) && member.getPwd().equals(findMember.getPwd())) {
            jsonObject.addProperty("status", "ok");
        } else {
            jsonObject.addProperty("status", "bad");
        }
        return new Gson().toJson(jsonObject);
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute MemberEntity member) {

        return "";
    }
}
