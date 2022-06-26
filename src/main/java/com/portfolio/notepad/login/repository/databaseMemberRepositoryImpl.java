package com.portfolio.notepad.login.repository;

import com.portfolio.notepad.login.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class databaseMemberRepositoryImpl implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public MemberEntity findMember(MemberEntity member) {
        return null;
    }

    @Override
    public List<MemberEntity> getMembers() {
        return memberMapper.getMemberAll();
    }

    @Override
    public boolean isMember(MemberEntity member) {
        log.info("isMember 함수 실행");
        MemberEntity findMember = memberMapper.isMember(member);
        log.info("findMember = {} ", findMember);

        return findMember != null;
    }

    @Override
    public void addMember(MemberEntity member) {
        memberMapper.addMember(member);
    }
}
