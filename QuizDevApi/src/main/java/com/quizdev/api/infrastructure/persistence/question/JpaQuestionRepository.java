package com.quizdev.api.infrastructure.persistence.question;

import com.quizdev.api.domain.quiz.entity.Question;
import com.quizdev.api.domain.quiz.repository.QuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuestionRepository extends JpaRepository<Question, Long>, QuestionRepository {

}
