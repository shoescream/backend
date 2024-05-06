package com.sideproject.shoescream.member.dto.request;

import lombok.Builder;

@Builder
public record MemberFindMemberInfoRequest(
        String email
) {
}
