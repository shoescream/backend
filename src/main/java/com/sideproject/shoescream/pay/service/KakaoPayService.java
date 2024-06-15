package com.sideproject.shoescream.pay.service;

import com.sideproject.shoescream.bid.entity.Bid;
import com.sideproject.shoescream.bid.repository.BidRepository;
import com.sideproject.shoescream.bid.repository.DealRepository;
import com.sideproject.shoescream.bid.util.DealMapper;
import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.pay.dto.KakaoApproveResponse;
import com.sideproject.shoescream.pay.dto.KakaoPayInfoRequest;
import com.sideproject.shoescream.pay.dto.KakaoReadyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private KakaoReadyResponse kakaoReadyResponse;
    private final BidRepository bidRepository;
    private final DealRepository dealRepository;
    private static final String cid = "TC0ONETIME";
    @Value("${pay.key}")
    private String adminKey;
    private final MemberRepository memberRepository;

    public KakaoReadyResponse kakaoPayReady(KakaoPayInfoRequest kakaoPayInfoRequest, String memberId) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "shoecream:mem:" + memberId);
        parameters.add("partner_user_id", "shoescream");
        parameters.add("item_name", kakaoPayInfoRequest.item_name());
        parameters.add("quantity", String.valueOf(kakaoPayInfoRequest.quantity()));
        parameters.add("total_amount", String.valueOf(kakaoPayInfoRequest.total_amount()));
        parameters.add("vat_amount", "0");
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/payment/success?bidNumber=" + kakaoPayInfoRequest.bidNumber() + "&memberId=" + memberId); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReadyResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponse.class);
        return kakaoReadyResponse;
    }

    public KakaoApproveResponse approveResponse(String pgToken, long bidNumber, String memberId) {

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReadyResponse.tid());
        parameters.add("partner_order_id", "shoecream:mem:" + memberId);
        parameters.add("partner_user_id", "shoescream");
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponse.class);


        // Buy_Bid를 삭제하고 -> 거래 완료 db로 저장하는 프로세스 구현
        completeDeal(memberId, bidNumber);
        return approveResponse;
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + adminKey;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    private void completeDeal(String memberId, long bidNumber) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Bid bid = bidRepository.findById(bidNumber)
                .orElseThrow(() -> new RuntimeException());
        dealRepository.save(DealMapper.toSuccessDeal(bid, member.getMemberNumber()));
        bidRepository.deleteById(bidNumber);
    }
}
