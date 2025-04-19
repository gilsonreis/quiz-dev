package com.quizdev.api.presentation.controller;

import com.quizdev.api.application.usecase.auth.AuthenticateUserUseCase;
import com.quizdev.api.presentation.request.LoginRequest;
import com.quizdev.api.presentation.response.TokenResponse;
import com.quizdev.api.application.usecase.auth.dto.AuthenticateUserUseCaseInput;
import com.quizdev.api.application.usecase.auth.dto.AuthenticateUserUseCaseOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        AuthenticateUserUseCaseInput input  = new AuthenticateUserUseCaseInput(request.getEmail(), request.getPassword());
        AuthenticateUserUseCaseOutput output = authenticateUserUseCase.execute(input);
        return ResponseEntity.ok(new TokenResponse(output.getToken()));
    }
}
