package com.onlineQuizApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineQuizApp.model.QuizAttempt;
import com.onlineQuizApp.model.User;



public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUser(User user);
}

