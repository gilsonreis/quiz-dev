package com.quizdev.api.application.usecase.user.dto;

public record RegisterUserUseCaseOutput (
        Long id,
        String name,
        String email,
        String hashToken
) { }
