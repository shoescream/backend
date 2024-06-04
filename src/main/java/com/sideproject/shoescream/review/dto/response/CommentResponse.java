package com.sideproject.shoescream.review.dto.response;

import com.sideproject.shoescream.review.entity.ReviewComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {
    private Long commentNumber;
    private String commentContent;
    private LocalDateTime createdAt;
    private String memberId;

    public static CommentResponse fromEntity(ReviewComment comment) {
        return CommentResponse.builder()
                .commentNumber(comment.getCommentNumber())
                .commentContent(comment.getCommentContent())
                .createdAt(comment.getCreatedAt())
                .memberId(comment.getMember().getMemberId())
                .build();
    }
}
