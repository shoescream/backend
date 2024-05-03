package com.sideproject.shoescream.member.dto.response;

import lombok.Builder;

@Builder
public record MemberResponse(
        String memberId,
        String email,
        String name

) {
}
