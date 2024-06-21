package com.zerobase.storereservation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("사용자 정보가 일치하지 않습니다", HttpStatus.NOT_FOUND),
    USER_AUTHORIZATION_FAIL("접근할 수 없는 사용자 권한입니다", HttpStatus.UNAUTHORIZED),
    BAD_REQUEST("유효하지않은 요청입니다", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTED("이미 존재하는 아이디입니다", HttpStatus.CONFLICT);
    private final String description;
    private final HttpStatus httpStatus;

    ErrorCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
