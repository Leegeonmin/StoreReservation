package com.zerobase.storereservation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

public class VisitStore {
    @Getter
    public static class Request{
        @NotNull
        @Size(min=11, max=11)
        private String phone;
        @NotNull
        private LocalDateTime visitedTime;
        @NotNull
        private Long storeId;
    }
}
