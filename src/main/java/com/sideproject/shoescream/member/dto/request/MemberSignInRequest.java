package com.sideproject.shoescream.member.dto.request;

import com.sideproject.shoescream.member.validation.MemberId;
import com.sideproject.shoescream.member.validation.Password;
import com.sideproject.shoescream.member.validation.ValidationGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberSignInRequest(
        @NotBlank(groups = ValidationGroup.NotBlankGroup.class)
        @MemberId(groups = ValidationGroup.PatternGroup.class)
        String memberId,

        @Password(groups = ValidationGroup.PatternGroup.class)
        String password
) {
}
