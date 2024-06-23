package com.zerobase.storereservation.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class DeleteStore {
    @Getter
    public static class Request{
        @NotNull
        private Long storeId;
    }
}
