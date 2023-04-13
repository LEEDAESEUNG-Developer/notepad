package com.portfolio.notepad.controller;

import com.portfolio.notepad.controller.form.member.MemberLoginForm;
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

    @GetMapping({"/", "/index"})
    public String index(@ModelAttribute("form") MemberLoginForm memberLoginForm,
                        @CookieValue(value = "id", required = false) String id,
                        @CookieValue(value = "idSave", required = false) Boolean idSave) {
        memberLoginForm.setUserId(id);
        memberLoginForm.setIdSave(idSave);
        return "index";
    }

}
