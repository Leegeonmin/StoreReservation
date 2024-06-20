package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@EntityListeners(EntityListeners.class)
@Entity(name = "Review")
public class ReviewEntity {
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private Long storeId;
    @NotNull
    private Long memberId;
    @NotNull
    private String content;

    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    @LastModifiedDate
    @NotNull
    private LocalDateTime modifiedDate;

}
