package com.sideproject.shoescream.member.configuration.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.global.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");
        if(exception == null) {
            setResponse(response, ErrorCode.TOKEN_NOT_EXIST);
        } else if(exception.equals(ErrorCode.TOKEN_EXPIRED_ERROR.name())) {
            setResponse(response, ErrorCode.TOKEN_EXPIRED_ERROR);
        } else if (exception.equals(ErrorCode.TOKEN_SIGNATURE_ERROR.name())) {
            setResponse(response, ErrorCode.TOKEN_SIGNATURE_ERROR);
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset-UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Response<?> errorResponse = Response.error(errorCode.getHttpStatus().toString(), errorCode.getMessage());
        String result = new ObjectMapper().writeValueAsString(errorResponse);
        response.getWriter().write(result);
    }
}
