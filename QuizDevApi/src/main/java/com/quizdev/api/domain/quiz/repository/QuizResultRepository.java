package com.quizdev.api.domain.quiz.repository;

import com.quizdev.api.domain.quiz.entity.QuizResult;

import java.util.List;
import java.util.Optional;

public interface QuizResultRepository {
     Optional<QuizResult> findById(Long id);
     List<QuizResult> findAllByUserId(Long userId);
     void deleteById(Long id);
}
