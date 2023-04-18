package com.portfolio.notepad.controller;

import com.portfolio.notepad.controller.request.member.MemberCreateForm;
import com.portfolio.notepad.controller.request.member.MemberFindPwdForm;
import com.portfolio.notepad.controller.request.member.MemberLoginForm;
import com.portfolio.notepad.controller.request.member.MemberPwdChangeForm;
import com.portfolio.notepad.controller.session.MemberSession;
import com.portfolio.notepad.exception.MemberNotFount;
import com.portfolio.notepad.service.MemberService;
import com.portfolio.notepad.util.cookie.CookieInfo;
import com.portfolio.notepad.util.cookie.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public String login(@Valid @ModelAttribute(name = "form") MemberLoginForm form, BindingResult bindingResult, HttpSession session, HttpServletResponse response) {

        if (bindingResult.hasErrors()){
            bindingResult.addError(new ObjectError("loginFail", "아이디, 비밀번호를 확인해주세요."));
            return "index";
        }

        CookieInfo saveCookie[] = new CookieInfo[2];
        String domain = "127.0.0.1";

        saveCookie[0] = new CookieInfo("id", form.getUserId(), domain);
        saveCookie[1] = new CookieInfo("idSave", Boolean.toString(form.getIdSave()), domain);

        cookieSaveAndDelete(response, form.getIdSave(), saveCookie);

        try { // 인터셉터를 이용하면 코드를 줄 일수 있지 않을까?
            MemberSession memberSession = new MemberSession(memberService.login(form.getUserId(), form.getPassword()));
            session.setAttribute("member", memberSession);
            return "redirect:/notes";
        } catch (IllegalStateException e) {
            bindingResult.addError(new ObjectError("loginFail", e.getMessage()));
            return "index";
        }
    }

    @GetMapping("/find/password")
    public String passwordFindForm(@ModelAttribute(name = "form") MemberFindPwdForm form) {
        return "findPassword";
    }

    @PostMapping("/find/password")
    public String passwordFind(@Valid @ModelAttribute(name = "form") MemberFindPwdForm form, BindingResult bindingResult){
        /**
         * DB에 아이디가 존재하는지 체크
         * 있으면 : 입력한 아이디에 비밀번호 변경
         * 없으면 : 회원가입 하라고 안내보내기
         */
        if(bindingResult.hasErrors()){
            bindingResult.addError(new ObjectError("form", "아이디 또는 비밀번호에 공백이 있습니다."));
            return "findPassword";
        }

        try {
            memberService.findMemberPasswordChange(form);
        } catch (MemberNotFount e){
            bindingResult.addError(new ObjectError("form", e.getMessage()));
            return "findPassword";
        }

        return "redirect:/";
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

    private void cookieSaveAndDelete(HttpServletResponse response, Boolean isSave, CookieInfo ...cookieInfos){
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
