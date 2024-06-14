package com.sideproject.shoescream.member.controller;

import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.member.dto.request.KaKaoSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberFindMemberInfoRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.*;
import com.sideproject.shoescream.member.service.EmailService;
import com.sideproject.shoescream.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public Response<String> mailCheck(@RequestParam String mail, Integer authNumber) {
        return Response.success(emailService.checkValidAuthByEmail(mail, authNumber));
    }

    @GetMapping("/signin/find-id")
    public Response<String> findMemberId(@RequestParam String mail) {
        return Response.success(memberService.findMemberId(mail));
    }

    @PostMapping("/kakao-login")
    public Response<MemberSignInResponse> kakaoLogin(@RequestBody KaKaoSignInRequest kaKaoSignInRequest) {
        return Response.success(memberService.kakaoLogin(kaKaoSignInRequest));
    }

    @PostMapping("/signin/find-password")
    public Response<String> findMemberPassword(@RequestBody MemberFindMemberInfoRequest memberFindMemberInfoRequest) {
        return Response.success(emailService.sendRandomPasswordMail(memberFindMemberInfoRequest));
    }

    @GetMapping("/my/buying")
    public Response<List<MyBuyingHistoryResponse>> getMyBuyingHistory(
            @RequestParam String status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Authentication authentication) {
        return Response.success(memberService.getMyBuyingHistory(status, startDate, endDate, authentication));
    }

    @GetMapping("/my/selling")
    public Response<List<MySellingHistoryResponse>> getMySellingHistory(
            @RequestParam String status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Authentication authentication) {
        return Response.success(memberService.getMySellingHistory(status, startDate, endDate, authentication));

    }

    @GetMapping("/my/notification")
    public Response<List<MyNotificationResponse>> getMyNotifications(Authentication authentication) {
        return Response.success(memberService.getMyNotifications(authentication.getName()));
    }

    @GetMapping("/my/notification/{notificationNumber}")
    public Response<MyNotificationResponse> getMyNotification(@PathVariable String notificationNumber, Authentication authentication) {
        return Response.success(memberService.getMyNotification(notificationNumber, authentication.getName()));
    }
}
