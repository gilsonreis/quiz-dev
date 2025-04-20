package com.quizdev.api.domain.quiz.repository;

import com.quizdev.api.domain.quiz.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Optional<Question> findById(Long id);
    List<Question> findAllQuestionsByTechnologyId(Long technologyId);
    List<Question> findAll();
    void deleteById(Long id);
}
