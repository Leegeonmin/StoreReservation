package com.zerobase.storereservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class SignUp {

    @Getter
    public static class Request {
        @NotBlank
        private String name;
        @NotBlank
        private String password;
        @NotBlank
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
