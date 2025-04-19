package com.quizdev.api.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "level", nullable = false)
    private String level;

    @ManyToOne
    @JoinColumn(name = "technology_id")
    private Technology technology;

    @Column(name = "is_xgh", nullable = false)
    private boolean isXgh;

    public Question() {}

    public Question(
            String title,
            String description,
            String level,
            Technology technology,
            boolean isXgh
    ) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.technology = technology;
        this.isXgh = isXgh;
    }


}
