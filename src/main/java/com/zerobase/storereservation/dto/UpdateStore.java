package com.zerobase.storereservation.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UpdateStore {
    @Getter
    public static class Request{
        @NotNull
        private Long storeId;
        @Nullable
        private String name;
        @Nullable
        private String address;
        @Nullable
        private String description;

    }
}
