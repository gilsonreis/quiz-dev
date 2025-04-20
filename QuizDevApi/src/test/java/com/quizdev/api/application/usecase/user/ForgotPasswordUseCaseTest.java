package com.quizdev.api.application.usecase.user;

import com.quizdev.api.application.usecase.user.dto.ForgotPasswordUseCaseInput;
import com.quizdev.api.domain.shared.service.MailService;
import com.quizdev.api.domain.shared.service.dto.MailServiceInput;
import com.quizdev.api.domain.shared.vo.Email;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

class ForgotPasswordUseCaseTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final MailService mailService = mock(MailService.class);
    private final ForgotPasswordUseCase useCase = new ForgotPasswordUseCase(userRepository, mailService);

    @Test
    void shouldSendEmailWhenUserExists() {
        // Arrange
        Email email = new Email("user@example.com");
        User user = new User();
        user.setEmail(email.getValue());
        user.setName("John Doe");
        user.setHashToken("abc123");

        when(userRepository.findByEmail(email.getValue())).thenReturn(Optional.of(user));

        // Act
        useCase.execute(new ForgotPasswordUseCaseInput(email));

        // Assert
        ArgumentCaptor<MailServiceInput> captor = ArgumentCaptor.forClass(MailServiceInput.class);
        verify(mailService, times(1)).send(captor.capture());

        MailServiceInput capturedInput = captor.getValue();
        assert capturedInput.to().equals(email.getValue());
        assert capturedInput.subject().equals("Recuperação de Senha - QuizDev");
        assert capturedInput.body().contains("http://localhost:8080/user/forgot-password?token=abc123");
    }

    @Test
    void shouldNotSendEmailWhenUserDoesNotExist() {
        // Arrange
        Email email = new Email("notfound@example.com");
        when(userRepository.findByEmail(email.getValue())).thenReturn(Optional.empty());

        // Act
        useCase.execute(new ForgotPasswordUseCaseInput(email));

        // Assert
        verify(mailService, never()).send(any());
    }
}