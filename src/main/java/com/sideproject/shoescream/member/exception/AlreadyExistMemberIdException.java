package com.sideproject.shoescream.member.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class AlreadyExistMemberIdException extends ApplicationException {

    public AlreadyExistMemberIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}
