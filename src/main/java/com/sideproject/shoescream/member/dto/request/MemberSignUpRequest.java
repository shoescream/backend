package com.sideproject.shoescream.member.dto.request;

public record MemberSignUpRequest(
        String userId,
        String email,
        String password,
        String name
) {
}
