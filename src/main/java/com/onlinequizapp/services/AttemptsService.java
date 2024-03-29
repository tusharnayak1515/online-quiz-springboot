package com.onlinequizapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onlinequizapp.dto.Leaderboard;
import com.onlinequizapp.dto.LeaderboardResponse;
import com.onlinequizapp.entities.Attempt;
import com.onlinequizapp.entities.Question;
import com.onlinequizapp.entities.Quiz;
import com.onlinequizapp.entities.User;
import com.onlinequizapp.repositories.AttemptsRepo;

@Service
public class AttemptsService {
    
    @Autowired
	private AttemptsRepo attemptsRepo;
    
    @Autowired
	private QuizService quizService;

    public Leaderboard getLeaderboardByQuizId(int quizId) {
        Optional<Quiz> isQuiz = this.quizService.findById(quizId);
        if(!isQuiz.isPresent()) {
            throw new UsernameNotFoundException("Quiz Not Found");
        }
        List<LeaderboardResponse> attempts = this.attemptsRepo.getLeaderBoardByQuiz(quizId);
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setQuizId(quizId);
        leaderboard.setAttempts(attempts);
        return leaderboard;
    }

    public Attempt startAttempt(Quiz quiz, User user) {
        Attempt attempt = new Attempt();
        attempt.setQuiz(quiz);
        attempt.setUser(user);
        return this.attemptsRepo.save(attempt);
    }

    public Question getCurrentQuestion(int attemptId) {
        Optional<Attempt> isAttempt = findById(attemptId);
        if(!isAttempt.isPresent()) {
            throw new UsernameNotFoundException("Attempt Not Found!");
        }

        Attempt attempt = isAttempt.get();

        return attempt.getCurrentQuestion();
    }

    public void setCurrentQuestion(int attemptId, Question question) {
        Optional<Attempt> isAttempt = findById(attemptId);
        if(!isAttempt.isPresent()) {
            throw new UsernameNotFoundException("Attempt Not Found!");
        }

        Attempt attempt = isAttempt.get();

        attempt.setCurrentQuestion(question);
        attemptsRepo.save(attempt);
    }

    public Optional<Attempt> findById(int id) {
        return this.attemptsRepo.findById(id);
    }

    public Attempt update(Attempt attemptDto) {
        Optional<Attempt> attempt = findById(attemptDto.getAttemptid());
        if(attempt.isPresent()) {
            attemptDto = this.attemptsRepo.save(attemptDto);
        }
        return attemptDto;
    }
}
