package com.quizdev.api.application.usecase.auth.dto;

public class AuthenticateUserUseCaseInput {
    private String username;
    private String password;

    public AuthenticateUserUseCaseInput(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
