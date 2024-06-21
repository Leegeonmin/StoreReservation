package com.zerobase.storereservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AddStore {
    @Getter
    public static class Request{
        @NotBlank
        private String name;
        @NotBlank
        private String address;
        private String description;
        @NotNull
        private Long memberId;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Response{
        private Long storeId;
    }
}
