package com.onlineQuizApp.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "questions")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String text;

	private String correctAnswer;

	@ElementCollection
	private List<String> options;

	@ManyToOne
	@JoinColumn(name = "quiz_id")
	@JsonIgnore
	@JsonBackReference
	private Quiz quiz;
}
