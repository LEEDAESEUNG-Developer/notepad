package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.request.member.MemberCreateForm;
import com.portfolio.notepad.controller.request.member.MemberFindForm;
import com.portfolio.notepad.controller.request.member.MemberFindPwdForm;
import com.portfolio.notepad.controller.request.member.MemberPwdChangeForm;
import com.portfolio.notepad.controller.response.FindPassword;
import com.portfolio.notepad.entity.Member;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberService {

    // 회원가입
    @Transactional
    Member register(MemberCreateForm form);

    // 로그인
    Member login(String id, String pwd);

    // 회원 찾기
    FindPassword checkPassword(MemberFindForm id);

    // 회원 비밀번호 변경
    @Transactional
    void changeMemberPwd(MemberPwdChangeForm form);

    // 비밀번호 찾기 -> 비밀번호 변경
    @Transactional
    void findMemberPasswordChange(MemberFindPwdForm form);

    // 회원 탈퇴
    @Transactional
    void deleteMember(Long memberId);
}
