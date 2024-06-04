package com.sideproject.shoescream.member.service;

import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.member.util.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 회원")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @DisplayName("회원 정보를 입력하면, 새로운 회원 정보를 저장하여 가입시키고 해당 회원 데이터를 리턴한다.")
    @Test
    void givenMemberParams_whenSaving_thenSavesMember() {
        // Given
        Member member = createMember("wnsdhqo");
        Member savedMember = createSigningUpMember();
        given(encoder.encode(member.getPassword())).willReturn("Gkrehd102!");
        given(memberRepository.save(member)).willReturn(savedMember);

        // When
        MemberResponse result = memberService.signUp(createMemberSignUpRequest(member));

        // Then
        assertThat(result)
                .hasFieldOrPropertyWithValue("memberId", member.getMemberId())
                .hasFieldOrPropertyWithValue("email", member.getEmail())
                .hasFieldOrPropertyWithValue("profileImage", member.getProfileImage())
                .hasFieldOrPropertyWithValue("name", member.getName());
    }

    @DisplayName("로그인이 정상동작 한다.")
    @Test
    void givenMemberParams_whenSignIn_thenLoginMember() {
        // Given
        Member member = createMember("wnsdhqo");
        given(encoder.matches(any(), any())).willReturn(true);

        when(memberRepository.findByMemberId(member.getMemberId())).thenReturn(Optional.of(member));
        when(jwtTokenUtil.generateToken(any())).thenReturn("asdfdasfasdfaasdfasdfassdfasdfsdfdasasdfasdffff");
        when(jwtTokenUtil.generateRefreshToken(any())).thenReturn("asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasd");
        // Then
        Assertions.assertDoesNotThrow(() -> memberService.signIn(createMemberSignInRequest(member)));
    }

    private MemberSignUpRequest createMemberSignUpRequest(Member member) {
        return MemberSignUpRequest.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .build();
    }

    private Member createMember(String memberId) {
        return Member.builder()
                .memberId(memberId)
                .password("Gkrehd102!")
                .email("wnsdhqo@naver.com")
                .name("배준오")
                .profileImage(null)
                .build();
    }

    private Member createSigningUpMember() {
        return createMember("wnsdhqo");
    }

    private MemberSignInRequest createMemberSignInRequest(Member member) {
        return MemberSignInRequest.builder()
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .build();
    }


}