package com.sideproject.shoescream.global.exception;

import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleMemberNotFoundException(MemberNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.valueOf(String.valueOf(e.getErrorCode().getHttpStatus())));
    }
}
