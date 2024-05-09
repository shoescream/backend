package com.sideproject.shoescream.member.dto.request;

public record KaKaoSignInRequest(
        Long id,
        String email,
        String nickname,
        String profile_image,
        String access_token,
        String refresh_token
) {
}
