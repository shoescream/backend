package com.sideproject.shoescream.global.exception;

import com.sideproject.shoescream.member.exception.AlreadyExistMemberIdException;
import com.sideproject.shoescream.member.exception.InvalidPasswordException;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.review.exception.InvalidReviewAccessRightException;
import com.sideproject.shoescream.review.exception.InvalidReviewCommentAccessRightException;
import com.sideproject.shoescream.review.exception.ReviewCommentNotFoundException;
import com.sideproject.shoescream.review.exception.ReviewNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFoundException(MemberNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(AlreadyExistMemberIdException.class)
    public ResponseEntity<?> handleAlreadyExistMemberIdException(AlreadyExistMemberIdException e) {
        return new ResponseEntity<>(e.getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e) {
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
