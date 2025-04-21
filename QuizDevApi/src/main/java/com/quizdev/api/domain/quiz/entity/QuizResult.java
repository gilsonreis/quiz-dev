package com.quizdev.api.domain.quiz.entity;

import com.quizdev.api.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "answered_at", nullable = false)
    private final LocalDateTime answeredAt = LocalDateTime.now();


    public QuizResult() {}

    public QuizResult(User user, int score) {
        this.user = user;
        this.score = score;
    }
}
