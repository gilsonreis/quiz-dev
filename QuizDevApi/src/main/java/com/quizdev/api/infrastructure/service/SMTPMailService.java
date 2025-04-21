package com.quizdev.api.infrastructure.service;

import com.quizdev.api.domain.shared.service.MailService;
import com.quizdev.api.domain.shared.service.dto.MailServiceInput;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class SMTPMailService implements MailService {

    private final JavaMailSender mailSender;

    public SMTPMailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(MailServiceInput input) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("no-reply@quizdev.com");
            helper.setTo(input.to());
            helper.setSubject(input.subject());
            helper.setText(input.body(), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
