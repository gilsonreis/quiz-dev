package com.quizdev.api.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizdev.api.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.findAll()
                .forEach(u -> userRepository.deleteById(u.getId()));
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        String json = """
        {
          "name": "Gilson Reis",
          "email": "gilson@example.com",
          "password": "secreta123"
        }
        """;

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Gilson Reis"))
            .andExpect(jsonPath("$.email").value("gilson@example.com"));
    }

    @Test
    void shouldReturn400IfEmailIsInvalid() throws Exception {
        String json = """
        {
          "name": "Usuário Teste",
          "email": "email-invalido",
          "password": "secreta123"
        }
        """;

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400IfPasswordIsTooShort() throws Exception {
        String json = """
        {
          "name": "Usuário Teste",
          "email": "teste@example.com",
          "password": "123"
        }
        """;

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn409IfEmailAlreadyExists() throws Exception {
        String json = """
        {
          "name": "Usuário Original",
          "email": "existente@example.com",
          "password": "secreta123"
        }
        """;

        // Primeiro cadastro
        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk());

        // Segundo cadastro com mesmo email
        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error").value("User already exists with email: existente@example.com"));
    }
}
