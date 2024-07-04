package com.sideproject.shoescream.member.validation;

import com.sideproject.shoescream.member.validation.ValidationGroup.NotBlankGroup;
import com.sideproject.shoescream.member.validation.ValidationGroup.PatternGroup;
import com.sideproject.shoescream.member.validation.ValidationGroup.SizeGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroup.class, PatternGroup.class, SizeGroup.class})
public interface ValidationSequence {

}