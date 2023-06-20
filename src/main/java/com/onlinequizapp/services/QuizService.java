package com.onlinequizapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinequizapp.entities.Quiz;
import com.onlinequizapp.repositories.QuizRepo;

@Service
public class QuizService {
	
	@Autowired
	private QuizRepo quizRepo;

	public Quiz save(Quiz entity) {
		return quizRepo.save(entity);
	}
	
	public Quiz update(Quiz quizDto) {
		Optional<Quiz> quiz = findById(quizDto.getQuizid());
		if(quiz.isPresent()) {
			Quiz myQuiz = quiz.get();
			return quizRepo.save(myQuiz);
		}
		return null;
	}

	public List<Quiz> findAll() {
		return quizRepo.findAll();
	}

	public Optional<Quiz> findById(int quizid) {
		return quizRepo.findById(quizid);
	}

}
