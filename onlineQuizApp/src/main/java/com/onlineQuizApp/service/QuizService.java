package com.onlineQuizApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineQuizApp.model.Question;
import com.onlineQuizApp.model.Quiz;
import com.onlineQuizApp.model.QuizAttempt;
import com.onlineQuizApp.model.User;
import com.onlineQuizApp.repository.QuestionRepository;
import com.onlineQuizApp.repository.QuizAttemptRepository;
import com.onlineQuizApp.repository.QuizRepository;

import java.util.List;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private QuestionRepository questionRepository;

	public List<Question> findQuestionsByQuizId(Long quizId) {
		return questionRepository.findByQuizId(quizId);
	}

	@Autowired
	private QuizAttemptRepository quizAttemptRepository;

	public List<Quiz> findAll() {
		return quizRepository.findAll();
	}

	public Quiz findById(Long id) {
		return quizRepository.findById(id).orElse(null);
	}

	public Quiz save(Quiz quiz) {
		return quizRepository.save(quiz);
	}

	public void delete(Long id) {
		quizRepository.deleteById(id);
	}

	public boolean existsById(Long id) {
		return quizRepository.existsById(id);
	}

	public int evaluateQuiz(Quiz quiz, List<String> answers) {
		int score = 0;
		for (int i = 0; i < quiz.getQuestions().size(); i++) {
			if (quiz.getQuestions().get(i).getCorrectAnswer().equals(answers.get(i))) {
				score++;
			}
		}
		return score;
	}

	public QuizAttempt saveQuizAttempt(String username, int score, Quiz quiz) {
		User user = new User();
		user.setUsername(username); // Ensure user exists
		QuizAttempt attempt = new QuizAttempt();
		attempt.setUser(user);
		attempt.setScore(score);
		attempt.setQuiz(quiz);
		return quizAttemptRepository.save(attempt);
	}
}
