package com.sideproject.shoescream.review.controller;

import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.review.dto.request.ReviewCommentPostRequest;
import com.sideproject.shoescream.review.dto.request.ReviewPostRequest;
import com.sideproject.shoescream.review.dto.response.ReviewCommentResponse;
import com.sideproject.shoescream.review.dto.response.ReviewResponse;
import com.sideproject.shoescream.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/{productNumber}")
    public Response<List<ReviewResponse>> getAllReviewsByProductNumber(@PathVariable Long productNumber) {
        return Response.success(reviewService.getAllReviewsByProductNumber(productNumber));
    }

    // reviewNumber로 통일
    @GetMapping("/review/{reviewNumber}")
    public Response<ReviewResponse> getReviewById(@PathVariable Long reviewNumber) {
        return Response.success(reviewService.getReviewById(reviewNumber));
    }

    @PostMapping("/review/post/{productNumber}")
    public Response<ReviewResponse> postReview(@RequestPart(value = "reviewPostRequest") ReviewPostRequest reviewPostRequest,
                                               @RequestPart(value = "reviewImage") List<MultipartFile> reviewImages,
                                               @PathVariable(name = "productNumber") Long productNumber,
                                               Authentication authentication) {
        return Response.success(reviewService.postReview(reviewPostRequest, reviewImages, productNumber, authentication.getName()));
    }

    // 게시글 수정
    @PostMapping("/review/update/{reviewNumber}")
    public Response<ReviewResponse> updateReview(@RequestBody ReviewPostRequest reviewPostRequest,
                                                 @PathVariable(value = "reviewNumber") Long reviewNumber,
                                                 Authentication authentication) {
        return Response.success(reviewService.updateReview(reviewPostRequest, reviewNumber, authentication.getName()));
    }

    // 게시글 삭제
    @PostMapping("/review/delete/{reviewNumber}")
    public Response<String> deleteReview(@PathVariable Long reviewNumber,
                               Authentication authentication) {
        return Response.success(reviewService.deleteReview(reviewNumber, authentication.getName()));
    }

    // 댓글 작성
    @PostMapping("/review/{reviewNumber}/comments")
    public Response<ReviewCommentResponse> postReviewComment(@PathVariable Long reviewNumber,
                                                             @RequestBody ReviewCommentPostRequest reviewCommentPostRequest,
                                                             Authentication authentication) {
        return Response.success(reviewService.postReviewComment(reviewCommentPostRequest, reviewNumber, authentication.getName()));
    }

    // 댓글 수정
    @PostMapping("/review/{commentNumber}/update/comment")
    public Response<ReviewCommentResponse> updateReviewComment(@RequestBody ReviewCommentPostRequest reviewCommentPostRequest,
                                                               @PathVariable(value = "commentNumber") Long commentNumber,
                                                               Authentication authentication) {
        // 예외 처리 수정 예정
//        if (authentication == null || authentication.getName() == null) {
//            return Response.failure("Authentication is required.");
//        }
        return Response.success(reviewService.updateReviewComment(reviewCommentPostRequest, commentNumber, authentication.getName()));
    }

    // 댓글 삭제
    @PostMapping("/review/{commentNumber}/delete/comment")
    public Response<String> deleteReviewComment(@PathVariable Long commentNumber,
                                                Authentication authentication) {
        return Response.success(reviewService.deleteReviewComment(commentNumber, authentication.getName()));
    }

}