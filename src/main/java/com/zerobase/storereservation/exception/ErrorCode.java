package com.zerobase.storereservation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    BAD_REQUEST("유효하지않은 요청입니다", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTED("이미 존재하는 아이디입니다", HttpStatus.CONFLICT);
    private final String description;
    private final HttpStatus httpStatus;

    ErrorCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
