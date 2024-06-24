package com.zerobase.storereservation.repository;

import com.zerobase.storereservation.domain.ReservationEntity;
import com.zerobase.storereservation.type.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("SELECT r FROM Reservation r WHERE " +
            "CAST(r.reservationDate AS date) = :date " +
            "AND r.storeId = :storeId " +
            "AND r.memberId = :memberId")
    List<ReservationEntity> findByLocalDateAAndStoreIdAndMemberId(
            @Param("date") LocalDate date,
            @Param("storeId") Long storeId,
            @Param("memberId") Long memberId
    );

    @Query("SELECT r FROM Reservation r WHERE " +
            "CAST(r.reservationDate AS date) = :date " +
            "AND r.storeId = :storeId " +
            "AND r.phone = :phone")
    List<ReservationEntity> findByLocalDateAndStoreIdAndPhone(
            @Param("date") LocalDate date,
            @Param("storeId") Long storeId,
            @Param("phone") String phone
);

    List<ReservationEntity> findAllByReservationStatusAndReservationDateBefore(ReservationStatus reservationStatus, LocalDateTime now);
}
