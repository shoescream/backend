package com.sideproject.shoescream.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //Token
    TOKEN_SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 입니다."),
    TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 만료 되었습니다.."),
    TOKEN_NOT_EXIST(HttpStatus.NOT_FOUND, "토큰이 존재하지 않습니다."),

    //MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    ALREADY_EXIST_USER_ID(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디입니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "올바르지 않은 비밀번호 형식입니다."),
    INVALID_MEMBER_NAME(HttpStatus.BAD_REQUEST, "올바르지 않은 이름입니다."),
    INVALID_USER_ID_AND_PASSWORD(HttpStatus.BAD_REQUEST, "아이디 혹은 비밀번호를 확인해주세요."),

    //PRODUCT
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),

    //REVIEW
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    INVALID_REVIEW_ACCESS_RIGHT(HttpStatus.UNAUTHORIZED, "유효하지 않은 접근입니다."),

    //REVIEW_COMMENT
    REVIEW_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰 댓글을 찾을 수 없습니다"),
    INVALID_REVIEW_COMMENT_ACCESS_RIGHT(HttpStatus.UNAUTHORIZED, "유효하지 않은 접근입니다."),

    //BID
    DEAL_NOT_FOUND(HttpStatus.NOT_FOUND, "거래 내역을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
