package com.quizdev.api.application.usecase.user.dto;

import com.quizdev.api.domain.shared.vo.Email;
import com.quizdev.api.domain.shared.vo.Password;

public record RegisterUserUseCaseInput (
        String name,
        Email email,
        Password password
) {
    public RegisterUserUseCaseInput {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
    }
}
