package com.sideproject.shoescream.review.exception;

import com.sideproject.shoescream.global.exception.ApplicationException;
import com.sideproject.shoescream.global.exception.ErrorCode;

public class ReviewCommentNotFoundException extends ApplicationException {

    public ReviewCommentNotFoundException(ErrorCode errorCode) {super(errorCode);}
}
