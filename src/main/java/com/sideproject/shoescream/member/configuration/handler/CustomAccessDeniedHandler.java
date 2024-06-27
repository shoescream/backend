package com.sideproject.shoescream.member.configuration.handler;

import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.configuration.filter.JwtTokenFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 4. 토큰 인증 후 권한 거부
        ErrorCode errorCode = ErrorCode.FORBIDDEN_JWT_TOKEN;
        JwtTokenFilter.setErrorResponse(response, errorCode);
    }
}
