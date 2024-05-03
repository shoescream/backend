package com.sideproject.shoescream.member.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class InvalidPasswordException extends ApplicationException {

    public InvalidPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
