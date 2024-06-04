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
}
