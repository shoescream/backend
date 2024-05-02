package com.sideproject.shoescream.global.repository;

import com.sideproject.shoescream.global.constant.AuthType;
import com.sideproject.shoescream.global.entity.EmailAuth;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthCustomRepository {
    Optional<EmailAuth> findValidAuthByEmail(String email, AuthType authType, Integer authNumber, LocalDateTime currentTime);
}
