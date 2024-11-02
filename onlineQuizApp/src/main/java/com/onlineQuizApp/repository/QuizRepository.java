package com.onlineQuizApp.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineQuizApp.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
