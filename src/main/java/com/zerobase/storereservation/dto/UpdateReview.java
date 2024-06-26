package com.zerobase.storereservation.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UpdateReview {
    @Getter
    public static class Request{
        @Nullable
        private String content;
        @Nullable
        private Long star;
        @NotNull
        private Long reviewId;
    }
}
