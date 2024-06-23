package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "Reservation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long storeId;
    @NotNull
    private Long memberId;

    @NotNull
    private LocalDateTime reservationDate;
    @Column(length = 11)
    @NotNull
    private String phone;

    @CreatedDate
    private LocalDateTime createdDate;
}
