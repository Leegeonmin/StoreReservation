package com.zerobase.storereservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class DeleteReview {
    @Getter
    public static class Request {
        @NotNull
        private Long reviewId;

    }
}
