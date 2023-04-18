package com.portfolio.notepad.controller;

import com.portfolio.notepad.controller.request.member.MemberLoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    /**
     * 1. 세션이 필요한 페이지를 따로 filter를 걸어서 "세션이 만료되었습니다. 재 로그인을 해주세요" 페이지 띄움
     * 2. 라우터 링크 없는 페이지 일 경우는 "없는 페이지 입니다." 페이지 띄움
     */

    @GetMapping({"/", "/index"})
    public String index(@ModelAttribute("form") MemberLoginForm memberLoginForm,
                        @CookieValue(value = "id", required = false) String id,
                        @CookieValue(value = "idSave", required = false) Boolean idSave) {
        memberLoginForm.setUserId(id);
        memberLoginForm.setIdSave(idSave);
        return "index";
    }

}
