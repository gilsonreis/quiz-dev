package com.quizdev.api.application.usecase.auth;

import com.quizdev.api.application.usecase.auth.dto.AuthenticateUserUseCaseInput;
import com.quizdev.api.application.usecase.auth.dto.AuthenticateUserUseCaseOutput;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.repository.UserRepository;
import com.quizdev.api.infrastructure.persistence.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticateUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticateUserUseCaseOutput execute(AuthenticateUserUseCaseInput input) {
        User user = userRepository.findByEmail(input.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid Credencials"));

        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credencials");
        }

        String token = jwtService.generateToken(user.getHashToken());
        return new AuthenticateUserUseCaseOutput(token);
    }
}
