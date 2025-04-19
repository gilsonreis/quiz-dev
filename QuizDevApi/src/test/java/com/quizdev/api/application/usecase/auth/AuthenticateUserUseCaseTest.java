package com.quizdev.api.application.usecase.auth;

import com.quizdev.api.application.usecase.auth.dto.AuthenticateUserUseCaseInput;
import com.quizdev.api.application.usecase.auth.dto.AuthenticateUserUseCaseOutput;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.repository.UserRepository;
import com.quizdev.api.infrastructure.persistence.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticateUserUseCaseTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticateUserUseCase useCase;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        useCase = new AuthenticateUserUseCase(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void shouldAuthenticateUserSuccessfully() {
        // given
        String email = "admin@quizdev.com";
        String password = "123456";
        String encodedPassword = "$2a$10$fakeencoded";
        String token = "jwt.token.mock";

        User user = new User("Admin", email, encodedPassword, UUID.randomUUID().toString());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(user.getHashToken())).thenReturn(token);

        // when
        AuthenticateUserUseCaseInput input = new AuthenticateUserUseCaseInput(email, password);
        AuthenticateUserUseCaseOutput output = useCase.execute(input);

        // then
        assertNotNull(output);
        assertEquals(token, output.getToken());
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        when(userRepository.findByEmail("notfound@domain.com")).thenReturn(Optional.empty());

        AuthenticateUserUseCaseInput input = new AuthenticateUserUseCaseInput("notfound@domain.com", "123");

        assertThrows(RuntimeException.class, () -> useCase.execute(input));
    }

    @Test
    void shouldThrowExceptionIfPasswordDoesNotMatch() {
        String email = "admin@quizdev.com";
        User user = new User("Admin", email, "$2a$10$encoded", UUID.randomUUID().toString());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", user.getPassword())).thenReturn(false);

        AuthenticateUserUseCaseInput input = new AuthenticateUserUseCaseInput(email, "wrongpass");

        assertThrows(RuntimeException.class, () -> useCase.execute(input));
    }
}