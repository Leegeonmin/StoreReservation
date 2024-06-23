package com.zerobase.storereservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GetStoreDetail {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request{
        private String name;
        private String address;
        private String description;
    }
}
