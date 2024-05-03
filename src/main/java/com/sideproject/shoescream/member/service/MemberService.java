package com.sideproject.shoescream.member.service;

import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.AlreadyExistUserIdException;
import com.sideproject.shoescream.member.exception.InvalidPasswordException;
import com.sideproject.shoescream.member.exception.InvalidUserIdAndPasswordException;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
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
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberResponse signUp(MemberSignUpRequest memberSignUpRequest) {
        checkUserId(memberSignUpRequest.userId());
        checkPassword(memberSignUpRequest.password());
        checkName(memberSignUpRequest.name());

        return MemberMapper.toMemberResponse(memberRepository.save(
                MemberMapper.toMember(memberSignUpRequest,
                        encoder.encode(memberSignUpRequest.password()))));
    }


    public MemberSignInResponse signIn(MemberSignInRequest memberSignInRequest) {
        Member member = memberRepository.findByUserId(memberSignInRequest.userId())
                .orElseThrow(
                        () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (!encoder.matches(memberSignInRequest.password(), member.getPassword())) {
            throw new InvalidUserIdAndPasswordException(ErrorCode.INVALID_USER_ID_AND_PASSWORD);
        }

        String accessToken = jwtTokenUtil.generateToken(member.getUserId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(member.getUserId());

        return MemberMapper.toSignInResponse(MemberMapper.toMemberResponse(member),
                MemberMapper.toTokenResponse(accessToken, refreshToken));
    }

    private void checkUserId(String userId) {
        if (memberRepository.existsByUserId(userId)) {
            throw new AlreadyExistUserIdException(ErrorCode.ALREADY_EXIST_USER_ID);
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
        if(!name.matches(namePattern)) {
            throw new IllegalArgumentException("올바른 이름 형식을 입력 해주세요.");
        }
    }
}
