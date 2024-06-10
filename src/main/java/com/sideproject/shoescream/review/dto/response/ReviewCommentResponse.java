package com.sideproject.shoescream.review.dto.response;

import com.sideproject.shoescream.review.entity.ReviewComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewCommentResponse {
    private Long commentNumber;
    private String commentContent;
    private LocalDateTime createdAt;
    private String memberId;

    public static ReviewCommentResponse fromEntity(ReviewComment reviewComment) {
        return ReviewCommentResponse.builder()
                .commentNumber(reviewComment.getCommentNumber())
                .commentContent(reviewComment.getCommentContent())
                .createdAt(reviewComment.getCreatedAt())
                .memberId(reviewComment.getMember().getMemberId())
                .build();
    }
}
