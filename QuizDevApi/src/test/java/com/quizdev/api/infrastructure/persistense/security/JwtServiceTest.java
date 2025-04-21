package com.quizdev.api.infrastructure.persistense.security;

import com.quizdev.api.infrastructure.persistence.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService("UpHXYn6W8RyDJFlb1F9grIoDw87uKo7wLRUhz8xgBrTrVm8Z5e3cc8d3kbsA2QabXElRI9koWkRGhxHy0WBclQ=");
    }

    @Test
    void shouldGenerateValidToken() {
        Long userId = 123L;
        String token = jwtService.generateToken(userId);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractUserIdFromToken() {
        Long userId = 456L;
        String token = jwtService.generateToken(userId);

        Long extractedUserId = jwtService.extractUserId(token);

        assertEquals(userId, extractedUserId);
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid() {
        String invalidToken = "invalid.token.value";

        assertThrows(Exception.class, () -> jwtService.extractUserId(invalidToken));
    }
}
