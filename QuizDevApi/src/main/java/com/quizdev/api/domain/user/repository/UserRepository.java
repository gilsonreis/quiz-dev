package com.quizdev.api.domain.user.repository;

import com.quizdev.api.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    <S extends User> S save(S user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    void deleteById(Long id);
    List<User> findAll();
    Optional<User> findByHashToken(String hashToken);
}
