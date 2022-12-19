package com.portfolio.notepad.service;

import com.portfolio.notepad.domain.MemberEntity;

public interface MemberService {

    // 회원가입
    public void register(MemberEntity member);

    // 로그인 확인
    public boolean isLogin(MemberEntity member);

    // 회원 찾기
    public MemberEntity findMember(MemberEntity member);

    // 회원 비밀번호 변경
    public int changeMemberPwd(MemberEntity member);

    // 회원 탈퇴
    public int deleteMember(MemberEntity member);
}
