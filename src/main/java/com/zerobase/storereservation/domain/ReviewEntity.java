package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
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
    private Long memberId;
    @NotNull
    private String content;
    @NotNull
    private Long star;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void update(String content, Long star) {
        if(content != null){
            this.content=content;
        }
        if(star != null){
            this.star = star;
        }
    }
}
