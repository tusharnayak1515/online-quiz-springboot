package com.onlinequizapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinequizapp.dto.QuestionResponse;
import com.onlinequizapp.entities.Question;
import com.onlinequizapp.entities.User;
import com.onlinequizapp.services.CustomUserDetailsService;
import com.onlinequizapp.services.QuestionsService;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {
	
	@Autowired
	private QuestionsService questionsService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping("/add")
	public ResponseEntity<QuestionResponse> addQuestion(@RequestBody Question question) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = customUserDetailsService.findOne(email);
		System.out.println(user.getRole());

		if(!user.getRole().equals("admin")) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Not Allowed!");
			return ResponseEntity.ok(myResponse);
		}

		question = questionsService.save(question);
		QuestionResponse myResponse = new QuestionResponse();
		myResponse.setSuccess(true);
		myResponse.setQuestion(question);
		return ResponseEntity.ok(myResponse);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<QuestionResponse> getAllQuestions() {
		List<Question> questions = questionsService.findAll();
		QuestionResponse myResponse = new QuestionResponse();
		myResponse.setSuccess(true);
		myResponse.setQuestions(questions);
		return ResponseEntity.ok(myResponse);
		
	}
}
