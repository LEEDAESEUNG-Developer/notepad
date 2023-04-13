package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.form.member.MemberCreateForm;
import com.portfolio.notepad.controller.form.member.MemberPwdChangeForm;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.exception.MemberCreateError;
import com.portfolio.notepad.exception.MemberNotFount;
import com.portfolio.notepad.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member register(MemberCreateForm form) {
        //중복회원 검사 로직
        memberJpaRepository.findByLoginId(form.getMemberId()).ifPresent(m -> {
            throw new MemberCreateError();
        });

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
                .orElseThrow(MemberNotFount::new);
    }

    @Override
    public void changeMemberPwd(MemberPwdChangeForm form) {
        Member findMember = memberJpaRepository.findById(form.getMemberId())
                .orElseThrow(MemberNotFount::new);

        findMember.updatePwd(form.getPwd());
    }

    @Override
    public void deleteMember(Long memberId) {
        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(MemberNotFount::new);
        memberJpaRepository.deleteById(findMember.getId());
    }
}
