package com.quizdev.api.application.usecase.auth.dto;

public class AuthenticateUserUseCaseOutput {
    private String token;

    public AuthenticateUserUseCaseOutput(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
