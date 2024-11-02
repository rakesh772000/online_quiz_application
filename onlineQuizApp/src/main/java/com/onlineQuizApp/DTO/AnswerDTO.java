package com.onlineQuizApp.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerDTO {
	private Long questionId;
	private String username;
	private int score;
	private List<String> selectedOptions;

}
