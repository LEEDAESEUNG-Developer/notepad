package com.portfolio.notepad.login.service;

import com.portfolio.notepad.login.MemberEntity;

import java.util.List;

public interface MemberService {

    public boolean isLogin(MemberEntity member);

    public void register(MemberEntity member);

    public MemberEntity findMember(MemberEntity member);

}
