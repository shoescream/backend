package com.sideproject.shoescream.member.dto.request;

public record MemberSignInRequest(
        String userId,
        String password
) {
}
