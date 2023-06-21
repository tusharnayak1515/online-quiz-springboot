package com.onlinequizapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/")
	public ResponseEntity<QuestionResponse> getAllQuestions() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.customUserDetailsService.findOne(email);

		if(!user.getRole().equals("admin")) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Not Allowed!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(myResponse);
		}

		List<Question> questions = this.questionsService.findAll();
		QuestionResponse myResponse = new QuestionResponse();
		myResponse.setSuccess(true);
		myResponse.setQuestions(questions);
		return ResponseEntity.ok(myResponse);
		
	}

	@PostMapping("/add")
	public ResponseEntity<QuestionResponse> addQuestion(@RequestBody Question question) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.customUserDetailsService.findOne(email);

		if(!user.getRole().equals("admin")) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Not Allowed!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(myResponse);
		}

		question = this.questionsService.save(question);
		QuestionResponse myResponse = new QuestionResponse();
		myResponse.setSuccess(true);
		myResponse.setQuestion(question);
		return ResponseEntity.ok(myResponse);
		
	}

	@PutMapping("/update")
	public ResponseEntity<QuestionResponse> updateQuestion(@RequestBody Question question) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.customUserDetailsService.findOne(email);

		if(!user.getRole().equals("admin")) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Not Allowed!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(myResponse);
		}

		question = this.questionsService.update(question);
		QuestionResponse myResponse = new QuestionResponse();
		myResponse.setSuccess(true);
		myResponse.setQuestion(question);
		return ResponseEntity.ok(myResponse);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<QuestionResponse> deleteQuestion(@PathVariable int id) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.customUserDetailsService.findOne(email);

		if(!user.getRole().equals("admin")) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Not Allowed!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(myResponse);
		}

		Optional<Question> question = this.questionsService.findById(id);
		if(!question.isPresent()) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Question not found!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myResponse);
		}
		else {
			this.questionsService.deleteById(id);
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(true);
			List<Question> questions = this.questionsService.findAll();
			myResponse.setQuestions(questions);
			return ResponseEntity.ok(myResponse);
		}
	}
}
