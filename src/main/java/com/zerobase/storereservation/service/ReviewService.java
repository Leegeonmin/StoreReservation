package com.zerobase.storereservation.service;

import com.zerobase.storereservation.domain.ReservationEntity;
import com.zerobase.storereservation.domain.ReviewEntity;
import com.zerobase.storereservation.exception.CustomException;
import com.zerobase.storereservation.exception.ErrorCode;
import com.zerobase.storereservation.repository.ReservationRepository;
import com.zerobase.storereservation.repository.ReviewRepository;
import com.zerobase.storereservation.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 리뷰 등록 로직
     * 예약id가 유효한지, 예약의 멤버와 입력의 멤버가 같은 지, 리뷰가 예약을 작성할 수 있는 상탱인지 검증 후 저장
     * @param memberId 토큰의 사용자 id
     * @param reservationId 예약 id
     * @param content 리뷰 내용
     * @param star 별점
     * @return 저장된 리뷰 id
     */
    public Long addReview(Long memberId, Long reservationId, String content, Long star) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        if (!Objects.equals(reservationEntity.getMemberId(), memberId)) {
            throw new CustomException(ErrorCode.USER_AUTHORIZATION_FAIL);
        }

        if(reservationEntity.getReservationStatus() != ReservationStatus.CONFIRM_VISIT){
            throw new CustomException(ErrorCode.Reservation_NOT_VISITED_CONFIRM);
        }
        ReviewEntity reviewEntity = reviewRepository.save(
                ReviewEntity.builder()
                        .reservationId(reservationEntity.getId())
                        .star(star)
                        .content(content)
                        .build()
        );

        return reviewEntity.getId();

    }
}
