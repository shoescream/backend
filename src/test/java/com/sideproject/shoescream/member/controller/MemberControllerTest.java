package com.sideproject.shoescream.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.member.dto.request.MemberFindMemberInfoRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.dto.response.TokenResponse;
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
    void 유저_회원가입_컨트롤러_테스트() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MemberSignUpRequest request = createMemberSignUpRequest();
        MemberResponse response = createMemberResponse();

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

    @Test
    @DisplayName("[POST] 로그인 컨트롤러 테스트")
    @WithMockUser
    void 유저_로그인_컨트롤러_테스트() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MemberSignInRequest request = createMemberSignInRequest();
        MemberSignInResponse response = createMemberSignInResponse();

        String requestContent = mapper.writeValueAsString(request);
        String responseContent = mapper.writeValueAsString(Response.success(response));

        given(memberService.signIn(any(MemberSignInRequest.class))).willReturn(response);

        mockMvc.perform(post("/signin").with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(content().json(responseContent));
    }

    @Test
    @DisplayName("[POST] 회원가입 시 이메일 인증 컨트롤러 테스트")
    @WithMockUser
    void 회원가입_이메일_인증_컨트롤러_테스트() throws Exception {
        // request => String, response => String
        String request = "wnsdhqo@naver.com";
        Integer response = 123456;

        given(emailService.sendAuthMail(any())).willReturn(response);

        mockMvc.perform(post("/mail").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[GET] 이메일 인증 번호 확인 컨트롤러 테스트")
    @WithMockUser
    void 이메일_인증_번호_확인_컨트롤러_테스트() throws Exception {
        String request1 = "wnsdhqo@naver.com";
        Integer request2 = 123456;
        String response = "이메일 인증 성공";

        given(emailService.checkValidAuthByEmail(any(), any())).willReturn(response);

        mockMvc.perform(get("/mail-check").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[GET] 아이디 찾기 컨트롤러 테스트")
    @WithMockUser
    void 유저_아이디_찾기_컨트롤러_테스트() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MemberFindMemberInfoRequest request = createMemberFindMemberInfoRequest();
        String response = "qownsdh";

        String requestContent = mapper.writeValueAsString(request);
        String responseContent = mapper.writeValueAsString(Response.success(response));

        given(memberService.findMemberId(any(MemberFindMemberInfoRequest.class))).willReturn(response);

        mockMvc.perform(get("/signin/find-id").with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(content().json(responseContent));

    }

    @Test
    @DisplayName("[POST] 비밀번호 찾기 컨트롤러 테스트")
    @WithMockUser
    void 비밀번호_찾기_컨트롤러_테스트() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MemberFindMemberInfoRequest request = createMemberFindMemberInfoRequest();
        String response = "임시 비밀번호 발급 메일이 성공적으로 전송되었습니다.";

        String requestContent = mapper.writeValueAsString(request);
        String responseContent = mapper.writeValueAsString(Response.success(response));

        given(emailService.sendRandomPasswordMail(any(MemberFindMemberInfoRequest.class))).willReturn(response);

        mockMvc.perform(post("/signin/find-password").with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(content().json(responseContent));
    }

    private MemberSignUpRequest createMemberSignUpRequest() {
        return MemberSignUpRequest.builder()
                .memberId("qownsdh")
                .email("wnsdhqo@naver.com")
                .password("1234")
                .name("배준오")
                .build();
    }

    private MemberResponse createMemberResponse() {
        return MemberResponse.builder()
                .memberId("qownsdh")
                .email("wnsdhqo@naver.com")
                .name("배준오")
                .build();
    }


    private MemberSignInRequest createMemberSignInRequest() {
        return MemberSignInRequest.builder()
                .memberId("qownsdh")
                .password("1234")
                .build();
    }

    private MemberSignInResponse createMemberSignInResponse() {
        return MemberSignInResponse.builder()
                .memberResponse(createMemberResponse())
                .tokenResponse(createTokenResponse())
                .build();
    }

    private TokenResponse createTokenResponse() {
        return TokenResponse.builder()
                .accessToken("123123123123123123")
                .refreshToken("123123123123123123")
                .build();
    }

    private MemberFindMemberInfoRequest createMemberFindMemberInfoRequest() {
        return MemberFindMemberInfoRequest.builder()
                .email("wnsdhqo@naver.com")
                .build();
    }
}