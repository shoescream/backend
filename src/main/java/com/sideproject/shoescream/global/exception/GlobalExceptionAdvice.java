package com.sideproject.shoescream.global.exception;

import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.review.exception.InvalidReviewAccessRightException;
import com.sideproject.shoescream.review.exception.InvalidReviewCommentAccessRightException;
import com.sideproject.shoescream.review.exception.ReviewCommentNotFoundException;
import com.sideproject.shoescream.review.exception.ReviewNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFoundException(MemberNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<?> handleReviewNotFoundException(ReviewNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(InvalidReviewAccessRightException.class)
    public ResponseEntity<?> handleReviewAccessRightException(InvalidReviewAccessRightException e) {
        return new ResponseEntity<>(e.getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(ReviewCommentNotFoundException.class)
    public ResponseEntity<?> handleReviewCommentNotFoundException(ReviewCommentNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(InvalidReviewCommentAccessRightException.class)
    public ResponseEntity<?> handleReviewCommentAccessRightException(InvalidReviewCommentAccessRightException e) {
        return new ResponseEntity<>(e.getMessage(), e.getErrorCode().getHttpStatus());
    }
}
