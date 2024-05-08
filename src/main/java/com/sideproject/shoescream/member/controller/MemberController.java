package com.sideproject.shoescream.member.controller;

import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.member.dto.KaKaoTokenDto;
import com.sideproject.shoescream.member.dto.request.MemberFindMemberInfoRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.service.EmailService;
import com.sideproject.shoescream.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public Response<MemberResponse> signUp(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        return Response.success(memberService.signUp(memberSignUpRequest));
    }

    @PostMapping("/signin")
    public Response<MemberSignInResponse> signIn(@RequestBody MemberSignInRequest memberSignInRequest) {
        return Response.success(memberService.signIn(memberSignInRequest));
    }

    @PostMapping("/mail")
    public Response<String> mailSend(String mail) {
        return Response.success("" + emailService.sendAuthMail(mail));
    }

    @GetMapping("/mail-check")
    public Response<String> mailCheck(String mail, Integer authNumber) {
        return Response.success(emailService.checkValidAuthByEmail(mail, authNumber));
    }

    @GetMapping("/signin/find-id")
    public Response<String> findMemberId(@RequestBody MemberFindMemberInfoRequest memberFindMemberInfoRequest) {
        return Response.success(memberService.findMemberId(memberFindMemberInfoRequest));
    }

    @PostMapping("/signin/find-password")
    public Response<String> findMemberPassword(@RequestBody MemberFindMemberInfoRequest memberFindMemberInfoRequest) {
        return Response.success(emailService.sendRandomPasswordMail(memberFindMemberInfoRequest));
    }

//    @GetMapping("/oauth/kakao")
//    public void getKakaoAuthorizationCode(Model model) {
//        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
//        model.addAttribute("location" + location);
//    }

    @GetMapping("/oauth/token")
    public Response<MemberSignInResponse> kakaoLogin(@RequestParam("code") String code) {
        KaKaoTokenDto kaKaoTokenDto = memberService.getKakaoAccessToken(code);
        return Response.success(memberService.kakaoLogin(kaKaoTokenDto.getAccess_token()));
    }
}
