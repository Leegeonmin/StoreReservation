package com.zerobase.storereservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class AddReview {
    @Getter
    public static class Request{
        @NotBlank
        private String content;
        @NotNull
        private Long star;
        @NotNull
        private Long reservationId;
    }
}
