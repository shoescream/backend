package com.sideproject.shoescream.member.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class InvalidMemberIdAndPasswordException extends ApplicationException {

    public InvalidMemberIdAndPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
