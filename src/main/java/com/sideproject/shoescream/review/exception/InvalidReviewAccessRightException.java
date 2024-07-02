package com.sideproject.shoescream.review.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class InvalidReviewAccessRightException extends ApplicationException {

    public InvalidReviewAccessRightException(ErrorCode errorCode) {super(errorCode);}
}
