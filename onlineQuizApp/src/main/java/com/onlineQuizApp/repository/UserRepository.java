package com.onlineQuizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineQuizApp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
