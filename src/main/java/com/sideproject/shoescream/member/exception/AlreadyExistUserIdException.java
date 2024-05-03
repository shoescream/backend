package com.sideproject.shoescream.member.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class AlreadyExistUserIdException extends ApplicationException {

    public AlreadyExistUserIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}
