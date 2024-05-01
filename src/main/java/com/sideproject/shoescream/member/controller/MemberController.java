package com.sideproject.shoescream.member.controller;

import com.sideproject.shoescream.global.dto.response.Response;
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
    public Response<MemberResponse> signUp(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        return Response.success(memberService.signUp(memberSignUpRequest));
    }

    @PostMapping("/signin")
    public Response<MemberSignInResponse> signIn(@RequestBody MemberSignInRequest memberSignInRequest) {
        return Response.success(memberService.signIn(memberSignInRequest));
    }
}
