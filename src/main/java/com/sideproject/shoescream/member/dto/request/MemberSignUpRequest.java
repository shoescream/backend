package com.sideproject.shoescream.member.dto.request;

public record MemberSignUpRequest(
        String memberId,
        String email,
        String password,
        String name
) {
}
