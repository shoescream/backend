package com.sideproject.shoescream.member.dto.response;

import lombok.Builder;

@Builder
public record MemberSignInResponse(

        MemberResponse memberResponse,
        TokenResponse tokenResponse
) {
}
