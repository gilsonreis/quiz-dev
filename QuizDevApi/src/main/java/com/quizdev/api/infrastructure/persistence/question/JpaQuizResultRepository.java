package com.quizdev.api.infrastructure.persistence.question;

import com.quizdev.api.domain.quiz.entity.QuizResult;
import com.quizdev.api.domain.quiz.repository.QuizResultRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuizResultRepository extends JpaRepository<QuizResult, Long>, QuizResultRepository {

}
