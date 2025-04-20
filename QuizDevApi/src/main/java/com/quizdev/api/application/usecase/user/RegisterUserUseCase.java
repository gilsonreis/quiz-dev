package com.quizdev.api.application.usecase.user;

import com.quizdev.api.application.usecase.user.dto.RegisterUserUseCaseInput;
import com.quizdev.api.application.usecase.user.dto.RegisterUserUseCaseOutput;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.exception.UserAlreadyExistsException;
import com.quizdev.api.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterUserUseCaseOutput execute(RegisterUserUseCaseInput input) {
        if (userRepository.findByEmail(input.email().getValue()).isPresent()) {
            throw new UserAlreadyExistsException(input.email().getValue());
        }

        User user = new User(
                input.name(),
                input.email().getValue(),
                passwordEncoder.encode(input.password().getValue()),
                UUID.randomUUID().toString()
        );

        User newUser = userRepository.save(user);

        return new RegisterUserUseCaseOutput(
                newUser.getId(),
                newUser.getName(),
                newUser.getEmail(),
                newUser.getHashToken()
        );
    }
}
