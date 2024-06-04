package com.sideproject.shoescream.review.entity;

import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_number")
    private Long reviewNumber;

    @ManyToOne
    @JoinColumn(name = "member_number", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_number", nullable = false)
    private Product product;

    @Column(name = "rating")
    private int rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewComment> reviewComments;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewImage> reviewImages;

    // No-args constructor for JPA
    protected Review() {}
}
