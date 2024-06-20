package com.zerobase.storereservation.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@EntityListeners(EntityListeners.class)
@Entity(name = "Reservation")
public class ReservationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;
    private Long memberId;

    private LocalDateTime reservationDate;
    private String phone;

    @CreatedDate
    private LocalDateTime createdDate;
}
