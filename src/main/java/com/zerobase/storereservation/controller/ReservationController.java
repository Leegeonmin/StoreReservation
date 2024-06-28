package com.zerobase.storereservation.controller;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.dto.ReserveStore;
import com.zerobase.storereservation.dto.VisitStore;
import com.zerobase.storereservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    /**
     * 매장 예약 API
     * @param request 예약시간, 예약 매장 id
     * @param member jwt에서 받아온 사용자 정보
     * @return 예약id
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> reserveStore(@RequestBody @Valid ReserveStore.Request request,
                                          @AuthenticationPrincipal MemberEntity member){
        log.info("ReserveStore request: {}", request );
        Long reservedId = reservationService.reserve(member.getId(), request.getStoreId(), request.getReserveTime());
        return ResponseEntity.ok().body("reservation successful! id = " + reservedId);
    }

    /**
     * 매장 방문 확인 API
     * @param request 전화번호, 예약시간,매장 id
     * @return 예약 확인 문자
     */
    @PostMapping("/visit")
    public ResponseEntity<?> visitStore(@RequestBody @Valid VisitStore.Request request){
        log.info("Visit request: {}", request );
        String reserveMember = reservationService.visitConfirm(request.getPhone(), request.getVisitedTime(),request.getStoreId());
        return ResponseEntity.ok().body(reserveMember +"님 예약이 확인되었습니다");
    }
}
