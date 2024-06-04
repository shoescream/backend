package com.sideproject.shoescream.review.controller;

import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.review.dto.request.ReviewPostRequest;
import com.sideproject.shoescream.review.dto.response.ReviewResponse;
import com.sideproject.shoescream.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/reviews/{reviewNumber}")
    public Response<ReviewResponse> getReviewById(@PathVariable Long reviewNumber) {
        return Response.success(reviewService.getReviewById(reviewNumber));
    }

    @PostMapping("/review-post/{productNumber}")
    public Response<ReviewResponse> postReview(@RequestBody ReviewPostRequest reviewPostRequest, Long productNumber, Authentication authentication) {
        return Response.success(reviewService.postReview(reviewPostRequest, productNumber, authentication.name()));
    }
}