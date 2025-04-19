package com.quizdev.api.infrastructure.persistence.question;

import com.quizdev.api.domain.quiz.entity.Question;
import com.quizdev.api.domain.repository.QuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuestionRepository extends JpaRepository<Question, Long>, QuestionRepository {
    // JpaRepository already provides methods for CRUD operations
    // You can add custom query methods here if needed
}
