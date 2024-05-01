package com.sideproject.shoescream.member.util;

import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.dto.response.TokenResponse;
import com.sideproject.shoescream.member.entity.Member;

public class MemberMapper {

    public static Member toMember(MemberSignUpRequest memberSignUpRequest, String encodePassword) {
        return Member.builder()
                .userId(memberSignUpRequest.userId())
                .password(encodePassword)
                .email(memberSignUpRequest.email())
                .name(memberSignUpRequest.name())
                .build();
    }

    public static MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    public static TokenResponse toTokenResponse(String accessToken, String refreshToken) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static MemberSignInResponse toSignInResponse(MemberResponse memberResponse, TokenResponse tokenResponse) {
        return MemberSignInResponse.builder()
                .memberResponse(memberResponse)
                .tokenResponse(tokenResponse)
                .build();
    }
}
