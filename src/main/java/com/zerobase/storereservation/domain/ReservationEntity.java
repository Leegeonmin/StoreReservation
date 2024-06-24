package com.zerobase.storereservation.domain;

import com.zerobase.storereservation.type.ReservationStatus;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    @NotNull
    private ReservationStatus reservationStatus;
    @CreatedDate
    private LocalDateTime createdDate;

    public boolean validateTime(LocalDateTime isOnTime) {
        LocalDateTime cutOffTime = reservationDate.minusMinutes(10);
        return !isOnTime.isAfter(cutOffTime);
    }

    public void confirmVisit() {
        this.reservationStatus = ReservationStatus.CONFIRM_VISIT;
    }

}
