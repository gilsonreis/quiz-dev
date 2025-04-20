package com.quizdev.api.application.usecase.user.dto;

import com.quizdev.api.domain.shared.vo.Email;
import com.quizdev.api.domain.shared.vo.Password;

public record ForgotPasswordUseCaseInput(Email email) {
}
