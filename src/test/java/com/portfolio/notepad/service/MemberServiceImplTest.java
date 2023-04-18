package com.portfolio.notepad.service;

import com.portfolio.notepad.controller.request.member.MemberCreateForm;
import com.portfolio.notepad.controller.request.member.MemberFindPwdForm;
import com.portfolio.notepad.controller.request.member.MemberPwdChangeForm;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.exception.MemberCreateError;
import com.portfolio.notepad.exception.MemberNotFount;
import com.portfolio.notepad.repository.MemberJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @BeforeEach
    void beforeEach(){
        memberJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입이 되어야한다")
    void 회원가입_성공(){
        //given
        String memberLoginId = "memberA";
        String memberPwd = "1234";

        MemberCreateForm createForm = new MemberCreateForm(memberLoginId, memberPwd);

        // when
        Member registerMember = saveMember(createForm);
        Member findMember = memberJpaRepository.findByLoginIdAndPwd(memberLoginId, memberPwd)
                .orElseThrow(MemberNotFount::new);

        //then
        assertThat(registerMember.getId()).isEqualTo(findMember.getId());
        assertThat(registerMember.getLoginId()).isEqualTo(findMember.getLoginId());
        assertThat(registerMember.getPwd()).isEqualTo(findMember.getPwd());
        assertThat(registerMember.getCreatedDate()).isEqualTo(findMember.getCreatedDate());
        assertThat(registerMember).isEqualTo(findMember);
    }

    @Test
    @DisplayName("중복회원인 경우는 에러가 발생해야한다")
    void 회원가입_실패(){
        //given
        String memberLoginId = "memberA";
        String memberPwd = "1234";

        MemberCreateForm createForm = new MemberCreateForm(memberLoginId, memberPwd);

        //then
        assertThatThrownBy(() -> {
            saveMember(createForm);
            saveMember(createForm);
        }).isInstanceOf(MemberCreateError.class);
    }

    @Test
    @DisplayName("DB에 있는 사용자라면 로그인이 되어야함")
    void 로그인_성공(){
        //given
        Member member = memberJpaRepository.save(new Member("member", "1234"));

        // when
        Member findMember = memberService.login("member", "1234");

        //then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    @DisplayName("DB에 없는 사용자라면 로그인이 실패되어야함")
    void 로그인_실패() {
        assertThatThrownBy(() -> {
            memberService.login("member", "1234");
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("비밀번호을 잃어버린경우 아이디 찾기로 비밀번호 변경 되어야함")
    void 회원찾기_성공(){
        //given
        String memberLoginId = "memberA";
        String memberPwd = "1234";
        String changePwd = "0000";

        //회원가입
        Member findMember = new Member(memberLoginId, memberPwd);
        memberJpaRepository.save(findMember);

        MemberFindPwdForm form = MemberFindPwdForm.builder()
                .loginId(memberLoginId)
                .changePassword(changePwd)
                .build();

        // when
        memberService.findMemberPasswordChange(form);

        //then
        Assertions.assertThat(findMember.getLoginId()).isEqualTo(memberLoginId);
        Assertions.assertThat(findMember.getPwd()).isEqualTo(changePwd);
    }

    @Test
    @DisplayName("아이디 찾기를 통해서 비밀번호 변경이 되어야함")
    void 비밀번호_변경_성공(){
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
    @DisplayName("회원이 삭제가 되어야함")
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

    @Test
    @DisplayName("없는 회원이 삭제할 경우 에러가 발생해야함")
    void 회원삭제_실패(){
        //expected
        Assertions.assertThatThrownBy(() -> {
            memberService.deleteMember(0L);
        }).isInstanceOf(MemberNotFount.class);
    }

    private Member saveMember(MemberCreateForm createForm) {
        return memberService.register(createForm);
    }
}