package com.sideproject.shoescream.member.dto.response;

import com.sideproject.shoescream.member.entity.Member;

public record MemberResponse(
        String userId,
        String email,
        String name

) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getUserId(),
                member.getEmail(),
                member.getName());
    }
}
