package com.portfolio.notepad.controller;

import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.exception.MemberNotFount;
import com.portfolio.notepad.repository.MemberJpaRepository;
import org.junit.jupiter.api.AfterEach;
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

    public static final String LOGIN_ID = "test";
    public static final String PASSWORD = "1234";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @AfterEach
    void afterEach() {
        memberJpaRepository.deleteAll();
    }

    @BeforeEach
    void beforeEach(){
        Member member = new Member(LOGIN_ID, PASSWORD);
        memberJpaRepository.save(member);
    }

    @DisplayName("/notes 접속한 비로그인 자는 / 페이지로 이동해야된다")
    @Test
    void login_x_fail() throws Exception {
        // expected
        mockMvc.perform(get("/notes"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인 하였을때 세션을 등록하고 /notes 페이지 이동해야된다")
    void login_success() throws Exception {
        // given
        Member findMember = getMember();

        MultiValueMap<String, String> param = createParam(findMember);

        //expected
        mockMvc.perform(post("/member/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(param))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/notes"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인을 할 때 아이디 저장이 되어어야함")
    void login_cookie_O() throws Exception {
        // given
        Member findMember = getMember();

        MultiValueMap<String, String> param = createParam(findMember);

        //expected
        mockMvc.perform(post("/member/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(param))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/notes"))
                .andExpect(MockMvcResultMatchers.cookie().value("id", LOGIN_ID))
                .andExpect(MockMvcResultMatchers.cookie().value("idSave", "true"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인을 할 때 아이이, 비밀번호 틀리면 접속이 안되어야함")
    void login_fail() throws Exception {
        //expected
        mockMvc.perform(post("/member/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("form", "userId", "password"))
                .andDo(MockMvcResultHandlers.print());
    }

    private Member getMember() {
        Member findMember = memberJpaRepository.findByLoginIdAndPwd(LOGIN_ID, PASSWORD)
                .orElseThrow(MemberNotFount::new);
        return findMember;
    }

    private MultiValueMap<String, String> createParam(Member findMember) {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("userId", findMember.getLoginId());
        param.add("password", findMember.getPwd());
        param.add("idSave", "true");
        return param;
    }
}