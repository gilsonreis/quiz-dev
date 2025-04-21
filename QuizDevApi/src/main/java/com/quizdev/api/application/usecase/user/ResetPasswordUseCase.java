package com.quizdev.api.application.usecase.user;

import com.quizdev.api.application.usecase.user.dto.ResetPasswordUseCaseInput;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.exception.UserNotFoundException;
import com.quizdev.api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResetPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(ResetPasswordUseCaseInput input) {
        User user = userRepository.findByHashToken(input.hashToken())
                .orElseThrow(UserNotFoundException::new);

        user.setPassword(passwordEncoder.encode(input.password().getValue()));
        user.setHashToken(UUID.randomUUID().toString());
        userRepository.save(user);
    }
}
