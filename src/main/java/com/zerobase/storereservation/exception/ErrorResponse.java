package com.zerobase.storereservation.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private final ErrorCode errorCode;
    private final String message;
    private String fieldError;
}
