package com.portfolio.notepad.controller;

import com.portfolio.notepad.controller.request.member.MemberFindForm;
import com.portfolio.notepad.controller.response.FindPassword;
import com.portfolio.notepad.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberService memberService;

    // 비밀번호 찾기
    @PostMapping("/checkPassword")
    public FindPassword checkPassword(@RequestBody MemberFindForm form) {
        FindPassword response = memberService.checkPassword(form);
        return response;
    }
}
