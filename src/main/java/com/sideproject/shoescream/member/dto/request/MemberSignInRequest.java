package com.sideproject.shoescream.member.dto.request;

public record MemberSignInRequest(
        String memberId,
        String password
) {
}
