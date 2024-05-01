package com.sideproject.shoescream.member.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(

        String accessToken,
        String refreshToken
) {
}
