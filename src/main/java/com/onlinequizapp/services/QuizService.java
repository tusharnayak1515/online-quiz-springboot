package com.onlinequizapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onlinequizapp.entities.Attempt;
import com.onlinequizapp.entities.Question;
import com.onlinequizapp.entities.Quiz;
import com.onlinequizapp.entities.User;
import com.onlinequizapp.repositories.AttemptsRepo;
import com.onlinequizapp.repositories.QuizRepo;

@Service
public class QuizService {
	
	@Autowired
	private QuizRepo quizRepo;
	
	@Autowired
	private AttemptsRepo attemptsRepo;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

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
	
	public void deleteById(int id) {
		Optional<Quiz> quiz = findById(id);
		if(quiz.isPresent()) {
			Quiz myQuiz = quiz.get();
			quizRepo.delete(myQuiz);
		}
	}

	public List<Quiz> findAll() {
		return quizRepo.findAll();
	}

	public Optional<Quiz> findById(int quizid) {
		return quizRepo.findById(quizid);
	}

	public Question startAttempt(int quizId, int userId) {
        Optional<Quiz> isQuiz = this.quizRepo.findById(quizId);
		if(!isQuiz.isPresent()) {
            throw new UsernameNotFoundException("Quiz Not Found!");
        }
		Quiz quiz = isQuiz.get();
        Attempt attempt = new Attempt();
        attempt.setQuiz(quiz);
		User user = this.customUserDetailsService.findById(userId);
        attempt.setUser(user);
        attemptsRepo.save(attempt);

        Question firstQuestion = quiz.getQuestions().get(0);

        attempt.setCurrentQuestion(firstQuestion);
        attemptsRepo.save(attempt);

        return firstQuestion;
    }

}
