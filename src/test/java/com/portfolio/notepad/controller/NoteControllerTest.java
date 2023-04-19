package com.portfolio.notepad.controller;

import com.portfolio.notepad.controller.request.note.NoteCreateForm;
import com.portfolio.notepad.controller.session.MemberSession;
import com.portfolio.notepad.entity.Member;
import com.portfolio.notepad.entity.Note;
import com.portfolio.notepad.exception.MemberNotFount;
import com.portfolio.notepad.repository.MemberJpaRepository;
import com.portfolio.notepad.repository.NoteJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.portfolio.notepad.entity.MemoType.BUSINESS;
import static com.portfolio.notepad.entity.MemoType.valueOf;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
@SpringBootTest
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private NoteJpaRepository noteJpaRepository;

    private final MockHttpSession session = new MockHttpSession();

    @BeforeEach
    void beforeEach(){
        memberJpaRepository.deleteAll();
        noteJpaRepository.deleteAll();

        Member member = new Member("test", "1234");
        memberJpaRepository.save(member);

        session.setAttribute("member", new MemberSession(member));
    }


    @Test
    @DisplayName("노트가 만들어져야한다")
    void create_note() throws Exception {
        // given
        Member member = getMember();
        NoteCreateForm createForm = new NoteCreateForm(BUSINESS, "제목입니다", "내용입니다");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("memberId", String.valueOf(member.getId()));
        params.add("memoType", String.valueOf(createForm.getMemoType()));
        params.add("title", createForm.getTitle());
        params.add("description", createForm.getDescription());

        // then
        mockMvc.perform(post("/noteAdd")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .params(params)
                        .session(session))
                .andExpect(redirectedUrl("notes"))
                .andDo(print());

        Note note = noteJpaRepository.findAll().get(0);

        Assertions.assertEquals(params.get("memberId").get(0), note.getMember().getId().toString());
        Assertions.assertEquals(valueOf(params.get("memoType").get(0)), note.getMemoType());
        Assertions.assertEquals(params.get("title").get(0), note.getTitle());
        Assertions.assertEquals(params.get("description").get(0), note.getDescription());
    }

    @Test
    @DisplayName("노트를 추가할 때 title, description 없을 경우 오류가 발생해야한다.")
    void note_add_fail() throws Exception {
        //given
        Member member = getMember();
        NoteCreateForm createForm = new NoteCreateForm(BUSINESS, null, null);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("memberId", String.valueOf(member.getId()));
        params.add("memoType", String.valueOf(createForm.getMemoType()));
        params.add("title", createForm.getTitle());
        params.add("description", createForm.getDescription());

        //expected
        mockMvc.perform(post("/noteAdd")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .params(params)
                        .session(session))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("noteCreateForm", "title"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("noteCreateForm", "description"))
                .andDo(print());

    }

    private Member getMember() {
        return memberJpaRepository.findByLoginId("test").orElseThrow(MemberNotFount::new);
    }


}
