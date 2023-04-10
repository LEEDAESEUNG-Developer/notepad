package com.portfolio.notepad.controller;

import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.exception.MemberNotFount;
import com.portfolio.notepad.repository.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @BeforeEach
    void beforeEach(){
        Member member = new Member("test", "1234");
        memberJpaRepository.save(member);
    }

    @DisplayName("/notes 접속한 비로그인 자는 / 페이지로 이동해야된다")
    @Test
    void login_x_fail() throws Exception {
        // expected
        mockMvc.perform(get("/notes"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인 하였을때 세션을 등록하고 /notes 페이지 이동해야된다")
    void login_success() throws Exception {
        // given
        Member findMember = memberJpaRepository.findByLoginIdAndPwd("test", "1234")
                .orElseThrow(MemberNotFount::new);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("id", findMember.getLoginId());
        param.add("pwd", findMember.getPwd());

        //expected
        mockMvc.perform(post("/member/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(param))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/notes"))
                .andDo(MockMvcResultHandlers.print());
    }
}