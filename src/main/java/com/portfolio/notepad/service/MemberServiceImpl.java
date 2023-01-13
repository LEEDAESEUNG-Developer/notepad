package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.form.MemberPwdChangeForm;
import com.portfolio.notepad.controller.form.MemberCreateForm;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member register(MemberCreateForm form) {
        //중복회원 검사 로직
        if(memberJpaRepository.findByLoginId(form.getMemberId()).isPresent()){
            throw new IllegalStateException("이 아이디로 가입된 유저가 있습니다");
        }

        Member createMember = new Member(form.getMemberId(), form.getMemberPwd());
        return memberJpaRepository.save(createMember);
    }

    @Override
    public Member login(String id, String pwd) {
        return memberJpaRepository.findByLoginIdAndPwd(id, pwd)
                .orElseThrow(() -> new IllegalStateException("올바른 아이디 또는 비밀번호를 입력하세요."));
    }

    @Override
    public Member findMember(String id) {
        return memberJpaRepository.findByLoginId(id)
                .orElseThrow(() -> new IllegalStateException("이 아이디로 가입된 유저가 없습니다"));
    }

    @Override
    public void changeMemberPwd(MemberPwdChangeForm form) {
        Member findMember = memberJpaRepository.findById(form.getMemberId())
                .orElseThrow(() -> new IllegalStateException("이 아이디로 가입된 유저가 없습니다"));

        findMember.updatePwd(form.getPwd());
    }

    @Override
    public void deleteMember(Long memberId) {
        Optional<Member> findMember = memberJpaRepository.findById(memberId);
        if(findMember.isPresent()){
            memberJpaRepository.deleteById(memberId);
        }
    }
}
