package com.sideproject.shoescream.member.dto.request;

import lombok.Builder;

@Builder
public record MemberSignInRequest(
        String memberId,
        String password
) {
}
