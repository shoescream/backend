package com.sideproject.shoescream.member.dto.request;

import com.sideproject.shoescream.member.validation.MemberId;
import com.sideproject.shoescream.member.validation.Password;
import com.sideproject.shoescream.member.validation.ValidationGroup.SizeGroup;
import com.sideproject.shoescream.member.validation.ValidationGroup.NotBlankGroup;
import com.sideproject.shoescream.member.validation.ValidationGroup.PatternGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberSignUpRequest(

        @NotBlank(groups = NotBlankGroup.class)
        @MemberId(groups = PatternGroup.class)
        String memberId,

        @NotBlank(groups = NotBlankGroup.class)
        @Email(message = "유효하지 않은 이메일 입니다.", regexp = EMAIL_REGEX, groups = PatternGroup.class)
        String email,

        @Password(groups = PatternGroup.class)
        String password,

        @NotBlank(groups = NotBlankGroup.class)
        @Pattern(regexp = "[^0-9]*", message = "이름에 숫자는 입력할 수 없습니다.", groups = PatternGroup.class)
        @Size(min = 2, max = 20, message = "이름의 길이는 2 ~ 20자여야 합니다.", groups = SizeGroup.class)
        String name
) {
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
}
