package com.sideproject.shoescream.member.repository;

import com.sideproject.shoescream.member.constant.AuthType;
import com.sideproject.shoescream.member.entity.EmailAuth;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthCustomRepository {
    Optional<EmailAuth> findValidAuthByEmail(String email, AuthType authType, Integer authNumber, LocalDateTime currentTime);
}
