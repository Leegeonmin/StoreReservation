package com.zerobase.storereservation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ALREADY_RESERVATION_EXISTED("이미 당일 예약되어 있습니다", HttpStatus.BAD_REQUEST),
    STORE_NOT_FOUND("매장 정보가 유효하지 않습니다", HttpStatus.NOT_FOUND),
    CEO_UNMATCHED("입력한 id가 매장 CEO ID와 일치하지 않습니다", HttpStatus.FORBIDDEN),
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
