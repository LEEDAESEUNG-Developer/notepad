package com.portfolio.notepad.repository;

import com.portfolio.notepad.domain.MemberEntity;
import java.util.List;

public interface MemberRepository {

    // 회원 가입
    public void addMember(MemberEntity member);

    // 회원 정보 가지고 오기
    public MemberEntity findMember(MemberEntity member);

    // 회원들을 가지고 오기
    public List<MemberEntity> getMembers();

    /**
     * 로그인 성공여부
     * @return 로그인 성공시 true, 실패시 false
     */
    public boolean isMember(MemberEntity member);

    // 회원 비밀번호 변경
    public int changeMemberPwd(MemberEntity member);

    // 회원 탈퇴
    public void deleteMember(MemberEntity member);
}
