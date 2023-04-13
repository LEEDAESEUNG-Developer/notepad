package com.portfolio.notepad.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.portfolio.notepad.controller.form.member.MemberCreateForm;
import com.portfolio.notepad.controller.form.member.MemberFindForm;
import com.portfolio.notepad.controller.form.member.MemberLoginForm;
import com.portfolio.notepad.controller.form.member.MemberPwdChangeForm;
import com.portfolio.notepad.controller.session.MemberSession;
import com.portfolio.notepad.util.cookie.CookieInfo;
import com.portfolio.notepad.util.cookie.CookieUtil;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public String login(MemberLoginForm form, HttpSession session, HttpServletResponse response) {

        CookieInfo saveCookie[] = new CookieInfo[2];
        String domain = "127.0.0.1";

        saveCookie[0] = new CookieInfo("id", form.getUserId(), domain);
        saveCookie[1] = new CookieInfo("idSave", Boolean.toString(form.getIdSave()), domain);

        cookieSaveDelete(response, form.getIdSave(), saveCookie);

        MemberSession memberSession = new MemberSession(memberService.login(form.getUserId(), form.getPassword()));
        session.setAttribute("member", memberSession);

        return "redirect:/notes";
    }

    // 비밀번호 찾기
    @ResponseBody
    @PostMapping("/findPassword")
    public String findPassword(@RequestBody MemberFindForm form) {
        JsonObject jsonObject = new JsonObject();
        Member findMember = memberService.findMember(form.getLoginId());

        if (form.getLoginId().equals(findMember.getLoginId())) {
            jsonObject.addProperty("status", "ok");
        } else {
            jsonObject.addProperty("status", "bad");
        }
        return new Gson().toJson(jsonObject);
    }

    @PostMapping("/change/password")
    public String passwordChange(@ModelAttribute MemberPwdChangeForm form, @SessionAttribute("member") MemberSession memberSession){

        form.setMemberId(memberSession.getId());
        memberService.changeMemberPwd(form);
        
        return "redirect:/notes";
    }

    @PostMapping("/delete")
    public String delete(@SessionAttribute("member") MemberSession memberSession) {
        memberService.deleteMember(memberSession.getId());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

    private void cookieSaveDelete(HttpServletResponse response, Boolean isSave, CookieInfo ...cookieInfos){
        if(isSave){
            for (CookieInfo cookieInfo : cookieInfos) {
                CookieUtil.cookieCreate(cookieInfo, response);
            }
        } else {
            for (CookieInfo cookieInfo : cookieInfos) {
                CookieUtil.cookieDelete(cookieInfo, response);
            }
        }
    }

}
