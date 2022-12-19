package com.portfolio.notepad.service;

import com.portfolio.notepad.domain.MemberEntity;
import com.portfolio.notepad.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void register(MemberEntity member) {
        memberRepository.addMember(member);
    }

    @Override
    public boolean isLogin(MemberEntity member) {
        return memberRepository.isMember(member);
    }

    @Override
    public MemberEntity findMember(MemberEntity member) {
        return memberRepository.findMember(member);
    }

    @Override
    public int changeMemberPwd(MemberEntity member) {
        return memberRepository.changeMemberPwd(member);
    }

    @Override
    public int deleteMember(MemberEntity member) {
        memberRepository.deleteMember(member);
        return 1;
    }
}
