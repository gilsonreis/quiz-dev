package com.quizdev.api.infrastructure.persistence.question;

import com.quizdev.api.domain.quiz.entity.Technology;
import com.quizdev.api.domain.repository.TechnologyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTechnologyRepository extends JpaRepository<Technology, Long>, TechnologyRepository {
}
