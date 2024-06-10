package com.sideproject.shoescream.review.entity;

import com.sideproject.shoescream.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    // No-args constructor for JPA
    protected ReviewComment() {}

    // 정적 팩토리 메서드를 추가
    public static ReviewComment create(Member member, Review review, String commentContent) {
        ReviewComment reviewComment = new ReviewComment();
        reviewComment.member = member;
        reviewComment.review = review;
        reviewComment.commentContent = commentContent;
        reviewComment.createdAt = LocalDateTime.now();
        return reviewComment;
    }

}
