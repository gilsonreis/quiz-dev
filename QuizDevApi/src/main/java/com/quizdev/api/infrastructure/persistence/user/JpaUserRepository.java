package com.quizdev.api.infrastructure.persistence.user;

import com.quizdev.api.domain.user.entity.User;
import com.quizdev.api.domain.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    // JpaRepository already provides methods for CRUD operations
    // You can add custom query methods here if needed
}
