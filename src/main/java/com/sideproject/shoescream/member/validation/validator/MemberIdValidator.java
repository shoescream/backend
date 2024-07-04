package com.sideproject.shoescream.member.validation.validator;

import com.sideproject.shoescream.member.validation.MemberId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class MemberIdValidator implements ConstraintValidator<MemberId, String> {

    private String MEMBER_ID_REGEX;

    @Override
    public void initialize(MemberId constraintAnnotation) {
        this.MEMBER_ID_REGEX = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && Pattern.matches(MEMBER_ID_REGEX, value);
    }
}
