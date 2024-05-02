package com.sideproject.shoescream.member.dto.response;

import lombok.Builder;

@Builder
public record MemberResponse(
        String userId,
        String email,
        String name

) {
}
