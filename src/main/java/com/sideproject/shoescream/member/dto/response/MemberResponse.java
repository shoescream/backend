package com.sideproject.shoescream.member.dto.response;

import lombok.Builder;

@Builder
public record MemberResponse(
        Long memberNumber,
        String memberId,
        String email,
        String name,
        String profileImage) {
}
