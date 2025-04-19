package com.quizdev.api.domain.repository;

import com.quizdev.api.domain.quiz.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Optional<Question> save(Question question);
    Optional<Question> update(Question question);
    Optional<Question> findById(Long id);
    List<Question> findAll(Long userId);
    void deleteById(Long id);
}
