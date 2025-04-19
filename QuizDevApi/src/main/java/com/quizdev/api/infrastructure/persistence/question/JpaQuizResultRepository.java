package com.quizdev.api.infrastructure.persistence.question;

import com.quizdev.api.domain.quiz.entity.QuizResult;
import com.quizdev.api.domain.repository.QuizResultRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuizResultRepository extends JpaRepository<QuizResult, Long>, QuizResultRepository {

}
