package com.portfolio.notepad.login.repository;

import com.portfolio.notepad.login.MemberEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 로그인
    @Select("select * from member where memberId = #{member.id} and memberPwd = #{member.pwd}")
    @Results(id = "select_member_all", value = {
            @Result(property = "id", column = "memberId"),
            @Result(property = "pwd", column = "memberPwd")
    })
    MemberEntity isMember(@Param("member") MemberEntity member);

    // 회원가입
    @Insert("insert into member(memberId, memberPwd) values(#{member.id}, #{member.pwd})")
    void addMember(@Param("member") MemberEntity member);

    // 모든 사용자 계정 확인
    @Select("select * from member")
    @ResultMap("select_member_all")
    List<MemberEntity> getMemberAll();

}
