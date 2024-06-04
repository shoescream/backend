package com.sideproject.shoescream.review.service;

import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.global.service.S3Service;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.product.entity.Product;
import com.sideproject.shoescream.product.repository.ProductRepository;
import com.sideproject.shoescream.review.dto.request.ReviewPostRequest;
import com.sideproject.shoescream.review.dto.response.ReviewResponse;
import com.sideproject.shoescream.review.entity.Review;
import com.sideproject.shoescream.review.entity.ReviewImage;
import com.sideproject.shoescream.review.repository.ReviewImageRepository;
import com.sideproject.shoescream.review.repository.ReviewRepository;
import com.sideproject.shoescream.review.util.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final S3Service s3Service;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewImageRepository reviewImageRepository;

    public List<ReviewResponse> getAllReviewsByProductNumber(Long productNumber) {
        List<Review> reviews = reviewRepository.findByProductNumber(productNumber);
        return reviews.stream()
                .map(ReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ReviewResponse getReviewById(Long reviewNumber) {
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return ReviewResponse.fromEntity(review);
    }

    @Transactional
    public ReviewResponse postReview(ReviewPostRequest reviewPostRequest, List<MultipartFile> reviewImages, Long productNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Product product = productRepository.findById(productNumber)
                .orElseThrow(() -> new RuntimeException("no product"));
        List<String> reviewImagesUrl = s3Service.upload(reviewImages);
        Review review = reviewRepository.save(
                ReviewMapper.toReview(reviewPostRequest, member, product));
        List<String> imagesUrlList = new ArrayList<>();

        for (String imgUrl : reviewImagesUrl) {
            ReviewImage reviewImage = ReviewImage.builder()
                    .reviewImageUrl(imgUrl)
                    .review(review)
                    .build();
            reviewImageRepository.save(reviewImage);
            imagesUrlList.add(reviewImage.getReviewImageUrl());
        }
        return ReviewMapper.toReviewResponse(review, imagesUrlList);
    }

    @Transactional
    public ReviewResponse updateReview(ReviewPostRequest reviewPostRequest, Long reviewNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException());

        if (!Objects.equals(member.getMemberNumber(), review.getMember().getMemberNumber())) {
            throw new RuntimeException();
        }
        review.setReviewTitle(reviewPostRequest.reviewTitle());
        review.setReviewContent(reviewPostRequest.reviewContent());
        review.setRating(reviewPostRequest.rating());

        return ReviewMapper.toUpdateReviewResponse(review);
    }

    @Transactional
    public String deleteReview(Long reviewNumber, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Review review = reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException());

        if (!Objects.equals(member.getMemberNumber(), review.getMember().getMemberNumber())) {
            throw new RuntimeException();
        }

        reviewRepository.deleteById(reviewNumber);

        return "삭제 완료";
    }
}
