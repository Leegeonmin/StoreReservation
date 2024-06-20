package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@EntityListeners(EntityListeners.class)
@Entity(name = "Review")
public class ReviewEntity {
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;
    private Long memberId;
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
