package com.portfolio.notepad.repository;

import com.portfolio.notepad.domain.MemberEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMybatisMapper {

    // 회원가입
    @Insert("insert into member(memberId, memberPwd) values(#{member.id}, #{member.pwd})")
    void addMember(@Param("member") MemberEntity member);

    // 로그인
    @Select("select * from member where memberId = #{member.id} and memberPwd = #{member.pwd}")
    @Results(id = "select_member_all", value = {
            @Result(property = "id", column = "memberId"),
            @Result(property = "pwd", column = "memberPwd")
    })
    MemberEntity isMember(@Param("member") MemberEntity member);

    // 회원 검색
    @Select("select * from member where memberId = #{member.id}")
    @ResultMap("select_member_all")
    MemberEntity findMember(@Param("member") MemberEntity member);

    // 모든 사용자 계정 확인
    @Select("select * from member")
    @ResultMap("select_member_all")
    List<MemberEntity> getMemberAll();


    // 비밀번호 변경
    @Update("update member set memberPwd = #{member.pwd} where memberId = #{member.id}")
    int changePwdMember(@Param("member") MemberEntity member);

    // 회원 삭제
    @Delete("delete from member where memberId = #{member.id}")
    int deleteMember(@Param("member") MemberEntity member);

    // 회원 게시판 삭제
    @Delete("delete from note where memberId = #{member.id}")
    int deleteNote(@Param("member") MemberEntity member);
}
