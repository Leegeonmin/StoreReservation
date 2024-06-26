package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@EntityListeners(EntityListeners.class)
@Entity(name = "Review")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewEntity {
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long reservationId;
    @NotNull
    private String content;
    @NotNull
    private Long star;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
