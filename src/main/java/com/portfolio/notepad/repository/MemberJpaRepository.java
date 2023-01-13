package com.portfolio.notepad.repository;

import com.portfolio.notepad.entity.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Primary
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    // 회원 로그인 아이디 찾기
    Optional<Member> findByLoginId(String loginId);

    // 로그인 ID, Pwd
    Optional<Member> findByLoginIdAndPwd(String loginId, String pwd);
}
