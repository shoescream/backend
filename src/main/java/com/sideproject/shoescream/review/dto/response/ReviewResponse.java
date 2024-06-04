package com.sideproject.shoescream.review.dto.response;

import com.sideproject.shoescream.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import com.sideproject.shoescream.review.entity.ReviewImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ReviewResponse {
    private Long reviewNumber;
    private String memberId;
    private int rating;
    private LocalDateTime createdAt;
    private String reviewTitle;
    private String reviewContent;
    private int reviewCommentsCount;
    private List<String> reviewImages;

    public static ReviewResponse fromEntity(Review review) {
        return ReviewResponse.builder()
                .reviewNumber(review.getReviewNumber())
                .memberId(review.getMember().getMemberId())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .reviewTitle(review.getReviewTitle())
                .reviewContent(review.getReviewContent())
                .reviewCommentsCount(review.getReviewComments().size())
                .reviewImages(review.getReviewImages().stream().map(ReviewImage::getReviewImageUrl).toList())
                .build();
    }
}
