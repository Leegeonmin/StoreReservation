package com.zerobase.storereservation.dto;

import lombok.Getter;

public class SignIn {
    @Getter
    public static class Request{
        private String name;
        private String password;
    }
}
