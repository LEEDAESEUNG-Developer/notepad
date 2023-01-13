package com.portfolio.notepad.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"loginId", "pwd"})
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private java.lang.Long id;

    private String loginId;
    private String pwd;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    public Member(String loginId, String pwd) {
        this.loginId = loginId;
        this.pwd = pwd;
    }

    //== 비즈니스 로직 ==//

    /**
     * 비밀번호 수정
     * @param pwd String
     */
    public void updatePwd(String pwd){
        this.pwd = pwd;
    }
}
