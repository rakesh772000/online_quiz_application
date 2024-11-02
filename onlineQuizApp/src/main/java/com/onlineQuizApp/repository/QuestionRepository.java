package com.onlineQuizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineQuizApp.model.Question;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findByQuizId(Long quizId);
}
