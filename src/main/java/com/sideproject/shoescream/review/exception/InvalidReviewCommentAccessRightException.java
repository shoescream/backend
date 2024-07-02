package com.sideproject.shoescream.review.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class InvalidReviewCommentAccessRightException extends ApplicationException {

    public InvalidReviewCommentAccessRightException(ErrorCode errorCode) {
        super(errorCode);
    }
}
