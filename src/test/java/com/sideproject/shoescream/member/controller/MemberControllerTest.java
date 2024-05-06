package com.sideproject.shoescream.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.service.EmailService;
import com.sideproject.shoescream.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private EmailService emailService;

    @Test
    @DisplayName("[POST] 회원 가입 컨트롤러 테스트")
    @WithMockUser
    void signUp() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MemberSignUpRequest request = MemberSignUpRequest.builder()
                .memberId("qownsdh")
                .email("wnsdhqo@naver.com")
                .password("1234")
                .name("배준오")
                .build();

        MemberResponse response = MemberResponse.builder()
                .memberId("qownsdh")
                .email("wnsdhqo@naver.com")
                .name("배준오")
                .build();

        String requestContent = mapper.writeValueAsString(request);
        String responseContent = mapper.writeValueAsString(Response.success(response));

        given(memberService.signUp(any(MemberSignUpRequest.class))).willReturn(response);

        mockMvc.perform(post("/signup").with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(content().json(responseContent));
    }
}