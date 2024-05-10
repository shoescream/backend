package com.sideproject.shoescream.member.service;

import com.sideproject.shoescream.global.exception.ErrorCode;

import com.sideproject.shoescream.member.dto.request.KaKaoSignInRequest;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder encoder;

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

    public MemberSignInResponse kakaoLogin(KaKaoSignInRequest kaKaoSignInRequest) {
        Member member = memberRepository.findByEmail(kaKaoSignInRequest.email()).orElse(null);
        if (member == null) {
            member = Member.builder()
                    .id(kaKaoSignInRequest.id())
                    .memberId("kakao" + kaKaoSignInRequest.id())
                    .email(kaKaoSignInRequest.email())
                    .name(kaKaoSignInRequest.nickname())
                    .profileImage(kaKaoSignInRequest.profile_image())
                    .build();
            memberRepository.save(member);
        }
        String accessToken = jwtTokenUtil.generateToken(member.getMemberId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(member.getMemberId());

        return MemberMapper.toSignInResponse(MemberMapper.toMemberResponse(member),
                MemberMapper.toTokenResponse(accessToken, refreshToken));
    }

    public String findMemberId(String mail) {
        Member member = memberRepository.findByEmail(mail)
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
