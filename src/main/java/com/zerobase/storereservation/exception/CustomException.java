package com.zerobase.storereservation.exception;

import lombok.Getter;

@Getter
public class CustomException extends  RuntimeException{
    private final ErrorCode errorCode;
    private final String errorMessage;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
