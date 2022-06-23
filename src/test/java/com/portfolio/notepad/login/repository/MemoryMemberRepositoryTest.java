package com.portfolio.notepad.login.repository;

import com.portfolio.notepad.login.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

    MemoryMemberRepositoryImpl memoryMemberRepository = new MemoryMemberRepositoryImpl();

    @Test
    @DisplayName("회원가입 성공")
    void register(){
        // given
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId("userA");
        memberEntity.setPwd("userA");
        // when
        memoryMemberRepository.addMember(memberEntity);
        MemberEntity member = memoryMemberRepository.findMember(memberEntity);

        // then
        Assertions.assertThat(member).isEqualTo(memberEntity);
    }
}