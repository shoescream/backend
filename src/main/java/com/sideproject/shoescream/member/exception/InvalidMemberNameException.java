package com.sideproject.shoescream.member.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class InvalidMemberNameException extends ApplicationException {

    public InvalidMemberNameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
