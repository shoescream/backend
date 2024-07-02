package com.sideproject.shoescream.review.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class ReviewNotFoundException extends ApplicationException {

    public ReviewNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
