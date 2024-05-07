package com.sideproject.shoescream.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.dto.KaKaoTokenDto;
import com.sideproject.shoescream.member.dto.KakaoProfile;
import com.sideproject.shoescream.member.dto.request.MemberFindMemberInfoRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.*;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.member.util.JwtTokenUtil;
import com.sideproject.shoescream.member.util.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder encoder;
    public static final String KAKAO_CLIENT_ID = "cb87d198bac8bdd63f6684692e3d827c";
    public static final String KAKAO_CLIENT_SECRET = "HduZ5Cvc9TGDlLEgdWiJEfBeRdQTbXHa";
    public static final String KAKAO_REDIRECT_URI = "http://13.125.247.226:8080/login/oauth2/code/kakao";
    public static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    public static final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(userId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberResponse signUp(MemberSignUpRequest memberSignUpRequest) {
        checkMemberId(memberSignUpRequest.memberId());
        checkPassword(memberSignUpRequest.password());
        checkName(memberSignUpRequest.name());
        // 이메일 인증 여부 예외 처리

        return MemberMapper.toMemberResponse(memberRepository.save(
                MemberMapper.toMember(memberSignUpRequest,
                        encoder.encode(memberSignUpRequest.password()))));
    }

    public MemberSignInResponse signIn(MemberSignInRequest memberSignInRequest) {
        Member member = memberRepository.findByMemberId(memberSignInRequest.memberId())
                .orElseThrow(
                        () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (!encoder.matches(memberSignInRequest.password(), member.getPassword())) {
            throw new InvalidMemberIdAndPasswordException(ErrorCode.INVALID_USER_ID_AND_PASSWORD);
        }

        String accessToken = jwtTokenUtil.generateToken(member.getMemberId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(member.getMemberId());

        return MemberMapper.toSignInResponse(MemberMapper.toMemberResponse(member),
                MemberMapper.toTokenResponse(accessToken, refreshToken));
    }

    public Member kakaoLogin(String kakaoAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        KakaoProfile kakaoProfile = getKakaoProfile(kakaoAccessToken);
        Member member = memberRepository.findByEmail(kakaoProfile.getKakaoAccount().getEmail()).orElse(null);

        // 처음 로그인일 경우
        if (member == null) {
            member = Member.builder()
                    .id(kakaoProfile.getId())
                    .email(kakaoProfile.getKakaoAccount().getEmail())
                    .name(kakaoProfile.getKakaoAccount().getNickname())
                    .build();
            memberRepository.save(member);
        }
        return member;
    }

    public KaKaoTokenDto getKakaoAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Http Response Body 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("code", code);
        params.add("client_secret", KAKAO_CLIENT_SECRET);

        // Header + Body
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //Json Parsing
        ObjectMapper objectMapper = new ObjectMapper();
        KaKaoTokenDto kakaoTokenDto = null;
        try {
            kakaoTokenDto = objectMapper.readValue(accessTokenResponse.getBody(), KaKaoTokenDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoTokenDto;
    }

    public KakaoProfile getKakaoProfile(String kakaoAccessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // POST 방식으로 API 서버에 요청 후 response 받아옴
        ResponseEntity<String> memberInfoResponse = restTemplate.exchange(
                KAKAO_USER_INFO_URI,
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // Json Parsing
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(memberInfoResponse.getBody(),
                    KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoProfile;
    }

    public String findMemberId(MemberFindMemberInfoRequest memberFindMemberInfoRequest) {
        Member member = memberRepository.findByEmail(memberFindMemberInfoRequest.email())
                .orElseThrow(
                        () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return member.getMemberId();
    }

    private void checkMemberId(String memberId) {
        if (memberRepository.existsByMemberId(memberId)) {
            throw new AlreadyExistMemberIdException(ErrorCode.ALREADY_EXIST_USER_ID);
        }
    }

    private void checkPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$&*])(?=\\S+$).{9,}$";
        if (!password.matches(passwordPattern)) {
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private void checkName(String name) {
        String namePattern = "[\\p{L}\\d]{1,10}";
        if (!name.matches(namePattern)) {
            throw new InvalidMemberNameException(ErrorCode.INVALID_MEMBER_NAME);
        }
    }
}
