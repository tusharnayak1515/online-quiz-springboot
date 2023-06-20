package com.onlinequizapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinequizapp.dto.QuizResponse;
import com.onlinequizapp.entities.Quiz;
import com.onlinequizapp.services.QuizService;

@RestController
@RequestMapping("/api/quizes")
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@GetMapping("/")
	public ResponseEntity<QuizResponse> getAllQuizes() {
		QuizResponse response = new QuizResponse();
		Iterable<Quiz> quizes = quizService.findAll();
		response.setSuccess(true);
		response.setQuizes(quizes);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/add")
	public ResponseEntity<QuizResponse> addQuiz(@RequestBody Quiz quiz) {
		quiz = quizService.save(quiz);
		QuizResponse response = new QuizResponse();
		response.setSuccess(true);
		response.setQuiz(quiz);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/update")
	public ResponseEntity<QuizResponse> updateQuiz(@RequestBody Quiz quiz) {
		quiz = quizService.update(quiz);
		QuizResponse response = new QuizResponse();
		response.setSuccess(true);
		response.setQuiz(quiz);
		return ResponseEntity.ok(response);
	}
	
}
