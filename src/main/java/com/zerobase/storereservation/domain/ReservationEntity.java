package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@EntityListeners(EntityListeners.class)
@Entity(name = "Reservation")
public class ReservationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
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
    @NotNull
    private LocalDateTime createdDate;
}
