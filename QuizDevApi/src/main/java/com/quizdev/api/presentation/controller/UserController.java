package com.quizdev.api.presentation.controller;

import com.quizdev.api.application.usecase.user.ForgotPasswordUseCase;
import com.quizdev.api.application.usecase.user.RegisterUserUseCase;
import com.quizdev.api.application.usecase.user.ResetPasswordUseCase;
import com.quizdev.api.application.usecase.user.dto.ForgotPasswordUseCaseInput;
import com.quizdev.api.application.usecase.user.dto.RegisterUserUseCaseInput;
import com.quizdev.api.application.usecase.user.dto.RegisterUserUseCaseOutput;
import com.quizdev.api.application.usecase.user.dto.ResetPasswordUseCaseInput;
import com.quizdev.api.domain.shared.vo.Email;
import com.quizdev.api.domain.shared.vo.Password;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.presentation.request.user.ForgotPasswordRequest;
import com.quizdev.api.presentation.request.user.RegisterRequest;
import com.quizdev.api.presentation.request.user.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    public UserController(
            RegisterUserUseCase registerUserUseCase,
            ForgotPasswordUseCase forgotPasswordUseCase,
            ResetPasswordUseCase resetPasswordUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.forgotPasswordUseCase = forgotPasswordUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserUseCaseOutput> registerUser(@RequestBody RegisterRequest request) {

        var input = new RegisterUserUseCaseInput(
                request.name(),
                new Email(request.email()),
                new Password(request.password())
        );

        RegisterUserUseCaseOutput output = registerUserUseCase.execute(input);
        return ResponseEntity.ok(output);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {

        ForgotPasswordUseCaseInput input = new ForgotPasswordUseCaseInput(new Email(request.email()));

        forgotPasswordUseCase.execute(input);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request,
            @RequestParam("hashToken") String hashToken
    ) {
        resetPasswordUseCase.execute(new ResetPasswordUseCaseInput(
            hashToken,
            new Password(request.password())
        ));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail()
        ));
    }
}
