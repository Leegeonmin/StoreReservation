package com.zerobase.storereservation.service;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.domain.ReservationEntity;
import com.zerobase.storereservation.exception.CustomException;
import com.zerobase.storereservation.exception.ErrorCode;
import com.zerobase.storereservation.repository.MemberRepository;
import com.zerobase.storereservation.repository.ReservationRepository;
import com.zerobase.storereservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    /**
     * 매장 예약 로직
     * memberId 검증, storeId 검증 후 사용자가 같은 매장에 하루에 여러번 예약하지 못하도록 로직을 추가하였습니다
     * @param memberId 사용자id
     * @param storeId 매장id
     * @param reserveTime 예약 시간
     * @return 예약id
     */
    @Transactional(readOnly = false)
    public Long reserve(Long memberId, Long storeId, LocalDateTime reserveTime) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        storeRepository.findById(storeId).orElseThrow(
                () -> new CustomException(ErrorCode.STORE_NOT_FOUND)
        );
        List<ReservationEntity> byDateAndStoreIdAndMemberId = reservationRepository.findByLocalDateAAndStoreIdAndMemberId(reserveTime.toLocalDate(), storeId, memberId);
        if(!byDateAndStoreIdAndMemberId.isEmpty()) throw new CustomException(ErrorCode.ALREADY_RESERVATION_EXISTED);

        ReservationEntity reservationEntity = reservationRepository.save(
                ReservationEntity.builder()
                        .memberId(memberId)
                        .storeId(storeId)
                        .phone(memberEntity.getPhone())
                        .reservationDate(reserveTime)
                        .build()
        );

        return reservationEntity.getId();
    }
}
