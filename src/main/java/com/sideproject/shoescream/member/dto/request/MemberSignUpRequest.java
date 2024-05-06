package com.sideproject.shoescream.member.dto.request;

import lombok.Builder;

@Builder
public record MemberSignUpRequest(
        String memberId,
        String email,
        String password,
        String name
) {
}
