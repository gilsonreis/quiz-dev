package com.quizdev.api.domain.repository;

import com.quizdev.api.domain.quiz.entity.Technology;

import java.util.List;
import java.util.Optional;

public interface TechnologyRepository {
    Optional<Technology> save(Technology technology);
    Optional<Technology> update(Technology technology);
    Optional<Technology> findById(Long id);
    List<Technology> findAll();
    void deleteById(Long id);
}
