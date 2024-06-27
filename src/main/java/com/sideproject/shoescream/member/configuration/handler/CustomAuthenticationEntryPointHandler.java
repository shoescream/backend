package com.sideproject.shoescream.member.configuration.handler;

import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.configuration.filter.JwtTokenFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 1. 토큰 없음 2. 시그니처 불일치
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            ErrorCode errorCode = ErrorCode.INVALID_JWT_TOKEN;
            JwtTokenFilter.setErrorResponse(response, errorCode);
        } else if (authorization.equals(ErrorCode.EXPIRED_JWT_TOKEN)) {
            // 3. 토큰 만료
            ErrorCode errorCode = ErrorCode.EXPIRED_JWT_TOKEN;
            JwtTokenFilter.setErrorResponse(response,errorCode);
        }
    }
}
