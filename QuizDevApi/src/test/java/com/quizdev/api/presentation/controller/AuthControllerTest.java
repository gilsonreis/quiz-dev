package com.quizdev.api.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.repository.UserRepository;
import com.quizdev.api.presentation.request.auth.LoginRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
@Rollback
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository.findByEmail("admin@quizdev.com")
                .ifPresent(u -> userRepository.deleteById(u.getId()));
    }

    @Test
    void shouldReturnTokenIfLoginIsValid() throws Exception {

        User user = userRepository.findByEmail("admin1@quizdev.com")
            .orElseGet(() -> {
                User newUser = new User(
                        "Admin",
                        "admin1@quizdev.com",
                        passwordEncoder.encode("123456"),
                        "1234567890"
                );
                return userRepository.save(newUser);
            });

        LoginRequest request = new LoginRequest("admin1@quizdev.com", "123456");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

    }

    @Test
    void shouldReturn401IfLoginIsInvalid() throws Exception {

        User user = userRepository.findByEmail("admin2@quizdev.com")
            .orElseGet(() -> {
                User newUser = new User(
                        "Admin",
                        "admin2@quizdev.com",
                        passwordEncoder.encode("123456"),
                        "1234567890"
                );
                return userRepository.save(newUser);
            });

        LoginRequest request = new LoginRequest("admin2@quizdev.com", "wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void shouldReturn401IfEmailDoesNotExist() throws Exception {
        LoginRequest request = new LoginRequest("nonexistent@quizdev.com", "123456");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn400IfLoginFieldsAreEmpty() throws Exception {
        LoginRequest request = new LoginRequest("", "");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}