package com.portfolio.notepad.login.service;

import com.portfolio.notepad.login.MemberEntity;
import com.portfolio.notepad.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public boolean isLogin(MemberEntity member) {
        return memberRepository.isMember(member);
    }

    @Override
    public void register(MemberEntity member) {
        memberRepository.addMember(member);
    }

    @Override
    public MemberEntity findMember(MemberEntity member) {
        return memberRepository.findMember(member);
    }
}
