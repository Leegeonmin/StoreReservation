package com.zerobase.storereservation.service;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.domain.ReservationEntity;
import com.zerobase.storereservation.exception.CustomException;
import com.zerobase.storereservation.exception.ErrorCode;
import com.zerobase.storereservation.repository.MemberRepository;
import com.zerobase.storereservation.repository.ReservationRepository;
import com.zerobase.storereservation.repository.StoreRepository;
import com.zerobase.storereservation.type.ReservationStatus;
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
     *
     * @param memberId    사용자id
     * @param storeId     매장id
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
        if (!byDateAndStoreIdAndMemberId.isEmpty()) throw new CustomException(ErrorCode.ALREADY_RESERVATION_EXISTED);

        ReservationEntity reservationEntity = reservationRepository.save(
                ReservationEntity.builder()
                        .memberId(memberId)
                        .storeId(storeId)
                        .phone(memberEntity.getPhone())
                        .reservationDate(reserveTime)
                        .reservationStatus(ReservationStatus.ENROLL)
                        .build()
        );

        return reservationEntity.getId();
    }

    /**
     * 매장 방문 확인 로직
     * validateReservation을 통해 예약이 유효한지 확인하고 예약의 상태를 변경합니다.
     *
     * @param phone       사용자 전화번호
     * @param visitedDate 방문 시간
     * @param storeId     매장 id
     * @return 방문자 이름
     */
    @Transactional(readOnly = false)
    public String visitConfirm(String phone, LocalDateTime visitedDate, Long storeId) {
        List<ReservationEntity> reservationEntities = validateReservation(visitedDate, storeId, phone);

        MemberEntity memberEntity = memberRepository.findById(reservationEntities.get(0).getMemberId()).get();
        reservationEntities.get(0).confirmVisit();

        return memberEntity.getName();
    }


    /**
     * 예약 방문 확인 전 유효성 검증
     * 입력을 통해 예약이 존재하는지, 예약 시간 10전 방문인지, 이미 방문 체크가 되었는지 확인
     *
     * @param visitedDate 방문 시간
     * @param storeId     매장 id
     * @param phone       전화번호
     * @return Reservation 객체
     */
    private List<ReservationEntity> validateReservation(LocalDateTime visitedDate, Long storeId, String phone) {
        List<ReservationEntity> reservationEntities = reservationRepository.findByLocalDateAndStoreIdAndPhone(visitedDate.toLocalDate(), storeId, phone);
        if (reservationEntities.isEmpty()) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        if (!reservationEntities.get(0).validateTime(visitedDate)) {
            throw new CustomException(ErrorCode.RESERVATION_CONFIRM_TOO_LATE);
        }
        if (reservationEntities.get(0).getReservationStatus() == ReservationStatus.CONFIRM_VISIT) {
            throw new CustomException(ErrorCode.ALREADY_RESERVATION_CONFIRMED);
        }
        return reservationEntities;
    }

    /**
     * 매일 저녁 12시 스케쥴링 실행하는 메서드
     * 예약 중 예약일이 지난 예약들은 NO_SHOW로 상태변경
     */
    @Transactional(readOnly = false)
    public void changeTimeOutReservationStatus() {
        List<ReservationEntity> TimeOutReservations = reservationRepository.findAllByReservationStatusAndReservationDateBefore
                (ReservationStatus.ENROLL, LocalDateTime.now());
        for(ReservationEntity entity : TimeOutReservations){
            entity.setReservationStatus(ReservationStatus.NO_SHOW);
        }
    }
}
