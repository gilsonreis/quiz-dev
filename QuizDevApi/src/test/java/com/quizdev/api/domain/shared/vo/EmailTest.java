package com.quizdev.api.domain.shared.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void shouldCreateEmailSuccessfully() {
        Email email = new Email("test@example.com");
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    void shouldThrowExceptionIfEmailIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Email(null));
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfEmailIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Email(""));
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfEmailIsInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Email("invalid-email"));
        assertEquals("Invalid email format", exception.getMessage());
    }
}
