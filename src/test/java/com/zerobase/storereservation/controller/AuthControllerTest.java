package com.zerobase.storereservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.storereservation.dto.SignUp;
import com.zerobase.storereservation.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {
    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 정보 저장 성공")
    void successSignup() throws Exception {
        //given
        given(memberService.signup(
                "name", "password", "01000000000", "CEO"
        )).willReturn(
                1L
        );
        //when
        //then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new SignUp.Request(
                                        "name", "password"
                                        , "01000000000", "CEO"
                                )
                        ))
                        .with(csrf())
                )

                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value("1"))
                .andDo(print());

    }
}