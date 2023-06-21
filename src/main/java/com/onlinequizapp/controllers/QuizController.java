package com.onlinequizapp.controllers;

import java.util.ArrayList;
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
import com.onlinequizapp.dto.QuizRequest;
import com.onlinequizapp.dto.QuizResponse;
import com.onlinequizapp.entities.Question;
import com.onlinequizapp.entities.Quiz;
import com.onlinequizapp.entities.User;
import com.onlinequizapp.services.CustomUserDetailsService;
import com.onlinequizapp.services.QuestionsService;
import com.onlinequizapp.services.QuizService;

@RestController
@RequestMapping("/api/quizes")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private QuestionsService questionsService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@GetMapping("/")
	public ResponseEntity<QuizResponse> getAllQuizes() {
		QuizResponse response = new QuizResponse();
		Iterable<Quiz> quizes = this.quizService.findAll();
		response.setSuccess(true);
		response.setQuizes(quizes);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addQuiz(@RequestBody QuizRequest myquiz) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.customUserDetailsService.findOne(email);

		if(!user.getRole().equals("admin")) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Not Allowed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
		}

		Quiz quiz = new Quiz();
		quiz.setTitle(myquiz.getTitle());
		List<Question> questions = new ArrayList<Question>();
		for(int id:myquiz.getQuestions()) {
			Optional<Question> q = this.questionsService.findById(id);
			if(q.isPresent()) {
				questions.add(q.get());
			}
		}
		quiz.setQuestions(questions);
		quiz = this.quizService.save(quiz);
		QuizResponse response = new QuizResponse();
		response.setSuccess(true);
		response.setQuiz(quiz);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.customUserDetailsService.findOne(email);

		if(!user.getRole().equals("admin")) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Not Allowed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
		}

		quiz = this.quizService.update(quiz);
		QuizResponse response = new QuizResponse();
		response.setSuccess(true);
		response.setQuiz(quiz);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteQuiz(@PathVariable int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.customUserDetailsService.findOne(email);

		if(!user.getRole().equals("admin")) {
			QuestionResponse myResponse = new QuestionResponse();
			myResponse.setSuccess(false);
			myResponse.setError("Not Allowed!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
		}

		Optional<Quiz> quiz = this.quizService.findById(id);
		if(quiz.isPresent()) {
			this.quizService.deleteById(id);
		}
		QuizResponse response = new QuizResponse();
		response.setSuccess(true);
		List<Quiz> quizes = this.quizService.findAll();
		response.setQuizes(quizes);
		return ResponseEntity.ok(response);
	}
	
}
