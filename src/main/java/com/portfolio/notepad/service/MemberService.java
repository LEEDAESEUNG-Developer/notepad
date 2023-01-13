package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.form.MemberPwdChangeForm;
import com.portfolio.notepad.controller.form.MemberCreateForm;
import com.portfolio.notepad.entity.Member;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberService {

    // 회원가입
    @Transactional
    public Member register(MemberCreateForm form);

    // 로그인
    public Member login(String id, String pwd);

    // 회원 찾기
    public Member findMember(String id);

    // 회원 비밀번호 변경
    @Transactional
    public void changeMemberPwd(MemberPwdChangeForm form);

    // 회원 탈퇴
    @Transactional
    public void deleteMember(Long memberId);
}
