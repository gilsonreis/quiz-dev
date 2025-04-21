package com.quizdev.api.domain.shared.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void shouldCreatePasswordSuccessfully() {
        Password password = new Password("strongPass123");
        assertEquals("strongPass123", password.getValue());
    }

    @Test
    void shouldThrowExceptionIfPasswordIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Password(null));
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfPasswordIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Password(""));
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfPasswordIsTooShort() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Password("123"));
        assertEquals("Password must be at least 6 characters long", exception.getMessage());
    }
}
