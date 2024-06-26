package com.zerobase.storereservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class SignUp {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        @NotBlank
        @Size(min = 11,max = 11)
        private String phone;
        @NotBlank
        @Pattern(regexp = "^(CEO|USER)$", message = "Role must be either CEO or USER")
        private String role;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Response{
        private Long userId;
    }
}
