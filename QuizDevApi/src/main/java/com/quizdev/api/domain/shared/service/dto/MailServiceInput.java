package com.quizdev.api.domain.shared.service.dto;

public record MailServiceInput(String to, String subject, String body, String name, String token) {
}
