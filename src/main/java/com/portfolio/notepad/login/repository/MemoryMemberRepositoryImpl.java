package com.portfolio.notepad.login.repository;

import com.portfolio.notepad.login.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class MemoryMemberRepositoryImpl implements MemberRepository {

    private final static Map<String, MemberEntity> repository = new HashMap<>();

    public MemoryMemberRepositoryImpl() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName("aaaa");
        memberEntity.setId("aaaa");
        memberEntity.setPwd("aaaa");
        addMember(memberEntity);
    }

    @Override
    public MemberEntity findMember(MemberEntity member) {
        if(member == null){ throw new NullPointerException("회원이 없습니다"); }
        return repository.get(member.getId());
    }

    @Override
    public List<MemberEntity> getMembers() {
        return null;
    }

    @Override
    public boolean isMember(MemberEntity member) {
        MemberEntity findMember = findMember(member);
        boolean isFindMember = false;
//        if(findMember == null){ throw new NullPointerException("회원이 없습니다"); }
        if(findMember == null){

        } else if (member.getId().equals(findMember.getId())) {
            if (member.getPwd().equals(findMember.getPwd())) isFindMember = true;
        }
        return isFindMember;
    }

    public void addMember(MemberEntity member){
        log.info("member = {} ", member);
        repository.put(member.getId(), member);
    }

    @Override
    public int changeMemberPwd(MemberEntity member) {
        return 0;
    }

    @Override
    public void deleteMember(MemberEntity member) {
    }
}
