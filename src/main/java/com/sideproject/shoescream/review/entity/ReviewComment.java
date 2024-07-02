package com.sideproject.shoescream.review.entity;

import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.review.exception.InvalidReviewCommentAccessRightException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_number")
    private Long commentNumber;

    @ManyToOne
    @JoinColumn(name = "member_number")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_number")
    private Review review;

    @Column(name = "comment_content", length = 1000)
    private String commentContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected ReviewComment() {}

    public void updateReview(String commentContent) {
        this.commentContent = commentContent;
    }

    public void validateReviewCommentAccessRight(Long accessorNumber) {
        if (!Objects.equals(accessorNumber, this.member.getMemberNumber())) {
            throw new InvalidReviewCommentAccessRightException(ErrorCode.INVALID_REVIEW_COMMENT_ACCESS_RIGHT);
        }
    }
}
