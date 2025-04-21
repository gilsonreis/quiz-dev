package com.quizdev.api.presentation.request.user;

public record ResetPasswordRequest(String password, String confirmPassword) {
    public ResetPasswordRequest {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}
