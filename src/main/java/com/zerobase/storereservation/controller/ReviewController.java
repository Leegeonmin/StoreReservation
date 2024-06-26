package com.zerobase.storereservation.controller;

import com.zerobase.storereservation.domain.MemberEntity;
import com.zerobase.storereservation.dto.AddReview;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addReview(@RequestBody @Valid AddReview.Request request,
                                       @AuthenticationPrincipal MemberEntity memberEntity){
        reviewService.addReview(memberEntity.getId(), request.getStoreId(),request.getContent(), request.getStar());

    }
}
