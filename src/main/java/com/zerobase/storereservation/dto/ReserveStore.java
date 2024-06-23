package com.zerobase.storereservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ReserveStore {
    @Getter
    public static class Request{
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime reserveTime;
        @NotNull
        private Long storeId;
    }
}
