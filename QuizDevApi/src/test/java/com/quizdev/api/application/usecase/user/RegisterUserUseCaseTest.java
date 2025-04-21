package com.quizdev.api.application.usecase.user;

import com.quizdev.api.application.usecase.user.dto.RegisterUserUseCaseInput;
import com.quizdev.api.application.usecase.user.dto.RegisterUserUseCaseOutput;
import com.quizdev.api.domain.shared.vo.Email;
import com.quizdev.api.domain.shared.vo.Password;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.exception.UserAlreadyExistsException;
import com.quizdev.api.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterUserUseCaseTest {
    private RegisterUserUseCase registerUserUseCase;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        registerUserUseCase = new RegisterUserUseCase(userRepository, passwordEncoder);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        Email email = new Email("newuser@user.com");
        Password password = new Password("123abc");
        RegisterUserUseCaseInput input = new RegisterUserUseCaseInput(
                "New User",
                email,
                password
        );

        // Mock the behavior of userRepository and passwordEncoder as needed
        when(userRepository.findByEmail(input.email().getValue())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(input.password().getValue())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenAnswer(i -> {
            var newUser = i.getArgument(0);
            Field idField = newUser.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(newUser, 1L);
            return newUser;
        });

        // execute useCase
        RegisterUserUseCaseOutput output = registerUserUseCase.execute(input);

        assertNotNull(output.id());
        assertEquals("New User", output.name());
        assertEquals(email.getValue(), output.email());
        assertNotNull(output.hashToken());

    }

    @Test
    void shouldThrowExceptionIfUserAlreadyExists() {
        Email email = new Email("existing@user.com");
        Password password = new Password("123abc");
        RegisterUserUseCaseInput input = new RegisterUserUseCaseInput(
                "Existing User",
                email,
                password
        );

        when(userRepository.findByEmail(input.email().getValue())).thenReturn(Optional.of(mock(User.class)));
        assertThrows(UserAlreadyExistsException.class, () -> registerUserUseCase.execute(input));

    }

}
