package com.sideproject.shoescream.pay.controller;

import com.sideproject.shoescream.pay.dto.KakaoApproveResponse;
import com.sideproject.shoescream.pay.dto.KakaoPayInfoRequest;
import com.sideproject.shoescream.pay.dto.KakaoReadyResponse;
import com.sideproject.shoescream.pay.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/ready")
    public KakaoReadyResponse readyToKakaoPay(@RequestBody KakaoPayInfoRequest kakaoPayInfoRequest, Authentication authentication) {
        return kakaoPayService.kakaoPayReady(kakaoPayInfoRequest, authentication.getName());
    }

    @GetMapping("/success")
    public ResponseEntity<?> afterPayRequest(@RequestParam("pg_token") String pgToken) {
        KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken);
        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }
}
