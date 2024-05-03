package com.sideproject.shoescream.global.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
