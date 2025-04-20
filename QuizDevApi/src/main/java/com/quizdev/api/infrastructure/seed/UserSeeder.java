package com.quizdev.api.infrastructure.seed;

import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@quizdev.com").isEmpty()) {
            User admin = new User(
                    "Admin",
                    "admin@quizdev.com",
                    passwordEncoder.encode("123456"),
                    UUID.randomUUID().toString()
            );
            userRepository.save(admin);
            System.out.println("✅ Admin user seeded with email: admin@quizdev.com / password: 123456");
        }
    }
}