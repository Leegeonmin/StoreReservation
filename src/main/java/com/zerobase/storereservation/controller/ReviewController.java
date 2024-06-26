package com.zerobase.storereservation.controller;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.dto.AddReview;
import com.zerobase.storereservation.dto.UpdateReview;
import com.zerobase.storereservation.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;


    /**
     * 리뷰 작성 API
     * @param request 리뷰내용, 평점, 예약id
     * @param memberEntity 토큰 사용자 정보
     * @return 리뷰 작성 성공 메시지
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> addReview(@RequestBody @Valid AddReview.Request request,
                                       @AuthenticationPrincipal MemberEntity memberEntity){
        Long reviewId = reviewService.addReview(memberEntity.getId(), request.getReservationId(), request.getContent(), request.getStar());
        return ResponseEntity.ok().body("review " + reviewId + " is saved successful");
    }


    /**
     * 리뷰 수정 API
     * @param request 리뷰내용, 평점, 리뷰id
     * @param memberEntity 토큰 사용자 ㅈㅇ보
     * @return 리뷰 수정 성공 메시지
     */
    @PatchMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateReview(@RequestBody @Valid UpdateReview.Request request,
                                               @AuthenticationPrincipal MemberEntity memberEntity){
        reviewService.updateReview(request.getReviewId(), request.getContent(), request.getStar(), memberEntity.getId());
        return ResponseEntity.ok().body("id " + request.getReviewId() + " update success");
    }

}
