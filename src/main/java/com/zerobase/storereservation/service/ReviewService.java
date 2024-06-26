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
    @Transactional(readOnly = false)
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
                        .memberId(memberId)
                        .reservationId(reservationId)
                        .star(star)
                        .content(content)
                        .build()
        );

        return reviewEntity.getId();

    }


    /**
     * 리뷰 수정 로직
     * 리뷰id에 맞는 리뷰가 존재하는지, 리뷰의 멤버 아이디와 토큰 아이디가 일치하는지 검증 후 업데이트
     * 값이 들어있지 않으면 변경하지 않는다
     * @param reviewId 리뷰 아이디
     * @param content 수정할 리뷰 내용
     * @param star 수정할 별점 
     * @param memberId 사용자 토큰 정보
     */
    @Transactional(readOnly = false)
    public void updateReview(Long reviewId, String content, Long star, Long memberId) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        if(!Objects.equals(reviewEntity.getMemberId(), memberId)){
            throw new CustomException(ErrorCode.USER_AUTHORIZATION_FAIL);
        }

        reviewEntity.update(content, star);
    }
}
