package com.quizdev.api.application.usecase.user;

import com.quizdev.api.application.usecase.user.dto.ResetPasswordUseCaseInput;
import com.quizdev.api.domain.shared.vo.Password;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.exception.UserNotFoundException;
import com.quizdev.api.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResetPasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private ResetPasswordUseCase resetPasswordUseCase;

    @BeforeEach
    void setUp() {
        resetPasswordUseCase = new ResetPasswordUseCase(userRepository, passwordEncoder);
    }

    @Test
    void shouldUpdatePasswordIfTokenIsValid() {
        // Arrange
        String hashToken = "valid-token";
        Password newPassword = new Password("newSecurePass123");
        User user = new User();
        user.setId(1L);
        user.setPassword(passwordEncoder.encode(new Password("oldPassword").getValue()));
        user.setHashToken(hashToken);

        when(userRepository.findByHashToken(hashToken)).thenReturn(Optional.of(user));

        // Act
        resetPasswordUseCase.execute(new ResetPasswordUseCaseInput(hashToken, newPassword));

        // Assert
        assertEquals(passwordEncoder.encode(newPassword.getValue()), user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionIfTokenIsInvalid() {
        // Arrange
        String hashToken = "invalid-token";
        Password password = new Password("newSecurePass123");

        when(userRepository.findByHashToken(hashToken)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () ->
                resetPasswordUseCase.execute(new ResetPasswordUseCaseInput(hashToken, password))
        );
    }

    @Test
    void shouldThrowExceptionIfPasswordIsInvalid() {
        // Arrange
        String hashToken = "valid-token";

//        // Simula que o token é válido
//        when(userRepository.findByHashToken(hashToken)).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Password("123") // menos de 6 caracteres
        );
    }
}
