package com.quizdev.api.application.usecase.auth.dto;

import com.quizdev.api.domain.shared.vo.Email;
import com.quizdev.api.domain.shared.vo.Password;
import lombok.Getter;

@Getter
public class AuthenticateUserUseCaseInput {
    private final Email username;
    private final Password password;

    public AuthenticateUserUseCaseInput(Email username, Password password) {
        this.username = username;
        this.password = password;
    }
}
