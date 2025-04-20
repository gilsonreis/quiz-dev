package com.quizdev.api.application.usecase.user;

import com.quizdev.api.application.usecase.user.dto.ForgotPasswordUseCaseInput;
import com.quizdev.api.domain.shared.service.MailService;
import com.quizdev.api.domain.shared.service.dto.MailServiceInput;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.repository.UserRepository;
import com.quizdev.api.infrastructure.helper.EmailTemplateRenderer;
import java.util.Map;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordUseCase {
    private final UserRepository userRepository;
    private final MailService mailService;

    public ForgotPasswordUseCase(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    public void execute(ForgotPasswordUseCaseInput input) {
        Optional<User> user = userRepository.findByEmail(input.email().getValue());

        if (user.isPresent()) {
            String subject = "Recuperação de Senha - QuizDev";
            String body = EmailTemplateRenderer.render("emails/forgot-password", Map.of(
                "name", user.get().getName(),
                "link", "http://localhost:8080/user/forgot-password?token=" + user.get().getHashToken()
            ));

            MailServiceInput mailServiceInput = new MailServiceInput(
                    user.get().getEmail(),
                    subject,
                    body,
                    user.get().getName(),
                    user.get().getHashToken()
            );

            mailService.send(mailServiceInput);
        }
    }
}
