package com.quizdev.api.domain.user.entity;

import com.quizdev.api.domain.quiz.entity.QuizResult;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(name = "hash_token", nullable = false)
    private String hashToken;

    @Column(name = "created_at", nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Setter
    @OneToMany(mappedBy = "user")
    private List<QuizResult> results;

    public User() {}

    public User(String name, String email, String password, String hashToken) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.hashToken = hashToken;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
