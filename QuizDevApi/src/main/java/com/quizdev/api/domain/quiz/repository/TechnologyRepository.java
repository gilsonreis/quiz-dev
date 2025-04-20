package com.quizdev.api.domain.quiz.repository;

import com.quizdev.api.domain.quiz.entity.Technology;

import java.util.List;
import java.util.Optional;

public interface TechnologyRepository {
    Optional<Technology> findById(Long id);
    List<Technology> findAll();
    void deleteById(Long id);
}
