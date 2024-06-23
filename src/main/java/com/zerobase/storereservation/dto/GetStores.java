package com.zerobase.storereservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class GetStores {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Response{
        private String name;
        private String address;
        private String description;
    }

}
