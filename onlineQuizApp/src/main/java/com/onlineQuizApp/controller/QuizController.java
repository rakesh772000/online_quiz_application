package com.onlineQuizApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.onlineQuizApp.DTO.AnswerDTO;
import com.onlineQuizApp.model.Question;
import com.onlineQuizApp.model.Quiz;
import com.onlineQuizApp.model.QuizAttempt;
import com.onlineQuizApp.service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@GetMapping
	public ResponseEntity<List<Quiz>> getAllQuizzes() {
		List<Quiz> quizzes = quizService.findAll();
		return ResponseEntity.ok(quizzes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
		Quiz quiz = quizService.findById(id);
		return quiz != null ? ResponseEntity.ok(quiz) : ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
		if (quiz.getQuestions() != null) {
			for (Question question : quiz.getQuestions()) {
				question.setQuiz(quiz);
			}
		}
		Quiz savedQuiz = quizService.save(quiz);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedQuiz);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quizDetails) {
		Quiz existingQuiz = quizService.findById(id);
		if (existingQuiz == null) {
			return ResponseEntity.notFound().build();
		}
		existingQuiz.setTitle(quizDetails.getTitle());
		existingQuiz.setQuestions(quizDetails.getQuestions());
		Quiz updatedQuiz = quizService.save(existingQuiz);
		return ResponseEntity.ok(updatedQuiz);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
		if (!quizService.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		quizService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{quizId}/attempt")
	public ResponseEntity<QuizAttempt> submitQuizAttempt(@PathVariable Long quizId, @RequestBody AnswerDTO answerDTO) {
		Quiz quiz = quizService.findById(quizId);
		if (quiz == null) {
			return ResponseEntity.notFound().build();
		}

		int score = quizService.evaluateQuiz(quiz, answerDTO.getSelectedOptions());
		QuizAttempt attempt = quizService.saveQuizAttempt(answerDTO.getUsername(), score, quiz);

		return ResponseEntity.ok(attempt);
	}

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/{quizId}/questions")
	public ResponseEntity<List<Question>> getQuestionsByQuizId(@PathVariable Long quizId) {
		List<Question> questions = quizService.findQuestionsByQuizId(quizId);
		return questions != null ? ResponseEntity.ok(questions) : ResponseEntity.notFound().build();
	}
}
