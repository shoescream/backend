package com.sideproject.shoescream.member.service;

import com.sideproject.shoescream.member.dto.request.MemberSignInRequest;
import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.dto.response.MemberSignInResponse;
import com.sideproject.shoescream.member.entity.Member;
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
                .orElseThrow(() -> new RuntimeException("User is not founded"));
    }

    public MemberResponse signUp(MemberSignUpRequest memberSignUpRequest) {
        return MemberMapper.toMemberResponse(memberRepository.save(
                MemberMapper.toMember(memberSignUpRequest,
                        encoder.encode(memberSignUpRequest.password()))));
    }

    public MemberSignInResponse signIn(MemberSignInRequest memberSignInRequest) {
        Member member = memberRepository.findByUserId(memberSignInRequest.userId())
                .orElseThrow(
                        () -> new RuntimeException("Invalid UserId and Password Exception"));

        if(!encoder.matches(memberSignInRequest.password(), member.getPassword())) {
            throw new RuntimeException("Invalid UserId and Password Exception");
        }

        String accessToken = jwtTokenUtil.generateToken(member.getUserId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(member.getUserId());

        return MemberMapper.toSignInResponse(MemberMapper.toMemberResponse(member),
                MemberMapper.toTokenResponse(accessToken, refreshToken));
    }
}
