package com.sideproject.shoescream.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_number")
    private Long reviewImageNumber;

    @ManyToOne
    @JoinColumn(name = "review_Number")
    private Review review;

    @Column(name = "review_image_url")
    private String reviewImageUrl;

    // No-args constructor for JPA
    protected ReviewImage() {}
}
