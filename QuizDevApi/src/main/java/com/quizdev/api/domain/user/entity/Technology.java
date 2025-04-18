package com.quizdev.api.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "technologies")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String logo;

    @OneToMany(mappedBy = "technology")
    private List<Question> questions;

    public Technology() {}

    public Technology(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

}
