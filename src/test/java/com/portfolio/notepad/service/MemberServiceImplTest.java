package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.form.MemberPwdChangeForm;
import com.portfolio.notepad.controller.form.MemberCreateForm;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.repository.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void 회원가입_성공(){
        //given
        String memberLoginId = "memberA";
        String memberPwd = "1234";

        MemberCreateForm createForm = new MemberCreateForm(memberLoginId, memberPwd);

        // when
        Member registerMember = saveMember(createForm);
        Member findMember = memberService.login(memberLoginId, memberPwd);

        //then
        assertThat(registerMember.getId()).isEqualTo(findMember.getId());
        assertThat(registerMember.getLoginId()).isEqualTo(findMember.getLoginId());
        assertThat(registerMember.getPwd()).isEqualTo(findMember.getPwd());
        assertThat(registerMember.getCreatedDate()).isEqualTo(findMember.getCreatedDate());
        assertThat(registerMember).isEqualTo(findMember);
    }

    @Test
    void 회원가입_실패(){
        //given
        String memberLoginId = "memberA";
        String memberPwd = "1234";

        MemberCreateForm createForm = new MemberCreateForm(memberLoginId, memberPwd);

        //then
        assertThatThrownBy(() -> {
            saveMember(createForm);
            saveMember(createForm);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 로그인_성공(){
        //given
        MemberCreateForm saveForm = new MemberCreateForm("member", "1234");

        // when
        Member saveMember = saveMember(saveForm);
        Member findMember = memberService.login("member", "1234");

        //then
        assertThat(saveMember).isEqualTo(findMember);
    }

    @Test
    void 로그인_실패() {
        assertThatThrownBy(() -> {
            memberService.login("member", "1234");
        }).isInstanceOf(IllegalStateException.class);
    }

    private Member saveMember(MemberCreateForm createForm) {
        return memberService.register(createForm);
    }

    @Test
    void 회원찾기_성공(){
        //given
        String memberLoginId = "memberA";
        String memberPwd = "1234";

        MemberCreateForm createForm = new MemberCreateForm(memberLoginId, memberPwd);

        // when
        Member registerMember = saveMember(createForm);

        Member findMember = memberService.findMember(memberLoginId);

        //then
        assertThat(registerMember).isEqualTo(findMember);
    }

    @Test
    void 회원찾기_실패(){
        assertThatThrownBy(() -> {
            memberService.findMember("memberA");
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 비밀번호_성공(){
        //given
        String memberLoginId = "memberA";
        String memberPwd = "1234";
        String changePwd = "1111";

        // when
        MemberCreateForm createForm = new MemberCreateForm(memberLoginId, memberPwd);
        Member saveMember = saveMember(createForm);

        memberService.changeMemberPwd(new MemberPwdChangeForm(saveMember.getId(), changePwd));
        Member findMember = memberService.login(memberLoginId, changePwd);

        //then
        assertThat(saveMember.getLoginId()).isEqualTo(findMember.getLoginId());
        assertThat(saveMember.getPwd()).isEqualTo(findMember.getPwd());
    }

    @Test
    void 회원삭제(){
        //given
        String memberLoginId = "memberA";
        String memberPwd = "1234";

        // when
        MemberCreateForm createForm = new MemberCreateForm(memberLoginId, memberPwd);
        Member saveMember = saveMember(createForm);

        //then
        memberService.deleteMember(saveMember.getId());
    }

}