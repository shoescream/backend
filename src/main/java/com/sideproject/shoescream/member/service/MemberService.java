package com.sideproject.shoescream.member.service;

import com.sideproject.shoescream.member.dto.request.MemberSignUpRequest;
import com.sideproject.shoescream.member.dto.response.MemberResponse;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    public MemberResponse signUp(MemberSignUpRequest memberSignUpRequest) {
        Member member = memberRepository.save(Member.of(
                memberSignUpRequest.userId(),
                encoder.encode(memberSignUpRequest.password()),
                memberSignUpRequest.email(),
                memberSignUpRequest.name()));
        return MemberResponse.from(member);
    }
}
