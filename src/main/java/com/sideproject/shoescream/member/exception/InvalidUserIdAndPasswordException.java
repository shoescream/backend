package com.sideproject.shoescream.member.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class InvalidUserIdAndPasswordException extends ApplicationException {

    public InvalidUserIdAndPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
