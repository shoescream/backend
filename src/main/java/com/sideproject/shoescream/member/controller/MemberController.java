package com.sideproject.shoescream.member.controller;

import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public MemberResponse signUp(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        return memberService.signUp(memberSignUpRequest);
    }

    @PostMapping("/signin")
    public MemberSignInResponse signIn(@RequestBody MemberSignInRequest memberSignInRequest) {
        return memberService.signIn(memberSignInRequest);
    }
}
