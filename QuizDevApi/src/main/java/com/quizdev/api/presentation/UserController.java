package com.quizdev.api.presentation;

import com.quizdev.api.application.usecase.user.RegisterUserUseCase;
import com.quizdev.api.application.usecase.user.dto.RegisterUserUseCaseInput;
import com.quizdev.api.application.usecase.user.dto.RegisterUserUseCaseOutput;
import com.quizdev.api.domain.shared.vo.Email;
import com.quizdev.api.domain.shared.vo.Password;
import com.quizdev.api.presentation.request.user.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
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
}
