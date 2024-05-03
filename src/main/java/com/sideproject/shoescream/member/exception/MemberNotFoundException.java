package com.sideproject.shoescream.member.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class MemberNotFoundException extends ApplicationException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
