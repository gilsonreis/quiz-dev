package com.quizdev.api.infrastructure.service;

import com.quizdev.api.domain.shared.service.dto.MailServiceInput;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SMTPMailServiceTest {

    private JavaMailSender mailSender;
    private SMTPMailService smtpMailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class); // não utilizado, mas injetado no construtor
        smtpMailService = new SMTPMailService(mailSender, templateEngine);
    }

    @Test
    void shouldCallJavaMailSenderSend() throws Exception {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        MailServiceInput input = new MailServiceInput(
                "john@example.com",
                "Test Subject",
                "<p>This is a test email</p>",
                "John Doe",
                "1234567890"
        );

        // Act
        smtpMailService.send(input);

        // Assert
        verify(mailSender, times(1)).send(mimeMessage);
        verify(mailSender, times(1)).createMimeMessage();
    }
}