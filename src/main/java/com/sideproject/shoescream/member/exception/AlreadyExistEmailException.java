package com.sideproject.shoescream.member.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class AlreadyExistEmailException extends ApplicationException {

    public AlreadyExistEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
