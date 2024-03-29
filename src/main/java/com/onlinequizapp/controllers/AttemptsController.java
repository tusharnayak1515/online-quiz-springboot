package com.onlinequizapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinequizapp.dto.Leaderboard;
import com.onlinequizapp.dto.NextQuestion;
import com.onlinequizapp.dto.QuizAttemptResponse;
import com.onlinequizapp.dto.QuizCompletedResponse;
import com.onlinequizapp.dto.StartAttemptRequest;
import com.onlinequizapp.dto.SubmitAnswerRequest;
import com.onlinequizapp.entities.Attempt;
import com.onlinequizapp.entities.Question;
import com.onlinequizapp.entities.Quiz;
import com.onlinequizapp.entities.User;
import com.onlinequizapp.services.AttemptsService;
import com.onlinequizapp.services.CustomUserDetailsService;
import com.onlinequizapp.services.QuizService;

@RestController
@RequestMapping("/api/attempts")
public class AttemptsController {
    
    @Autowired
	private QuizService quizService;

	@Autowired
	private AttemptsService attemptsService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/start")
    public ResponseEntity<?> startAttempt(@RequestBody StartAttemptRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.customUserDetailsService.findOne(email);
        Question question = this.quizService.startAttempt(request.getQuizId(), user.getId());
        NextQuestion firstQuestion = new NextQuestion(question.getQuestionid(),question.getQuestion(),question.getOption1(),question.getOption2(), question.getOption3(),question.getOption4());
        QuizAttemptResponse response = new QuizAttemptResponse(firstQuestion);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswer(@RequestBody SubmitAnswerRequest request) {

        Question currentQuestion = this.attemptsService.getCurrentQuestion(request.getAttemptId());

        boolean isCorrect = currentQuestion.getAnswer().equals(request.getAnswer()) ? true : false;

        Question nextQuestion = new Question();
        Optional<Attempt> isAttempt = this.attemptsService.findById(request.getAttemptId());
        if(!isAttempt.isPresent()) {
            throw new UsernameNotFoundException("Attempt Not Found!");
        }
        
        Attempt attempt = isAttempt.get();
        Quiz quiz = attempt.getQuiz();
        int index = quiz.getQuestions().indexOf(currentQuestion);
        if(isCorrect) {
            if(index + 1 >= quiz.getQuestions().size()) {
                attempt.setScore(attempt.getScore() + 1);
                attempt = this.attemptsService.update(attempt);
                Leaderboard leaderboard = this.attemptsService.getLeaderboardByQuizId(quiz.getQuizid());
                QuizCompletedResponse response = new QuizCompletedResponse(attempt.getScore(), "Quiz is completed",leaderboard);
                return ResponseEntity.ok(response);
            }
            else {
                attempt.setScore(attempt.getScore() + 1);
                attempt = this.attemptsService.update(attempt);
                nextQuestion = quiz.getQuestions().get(index+1);
                this.attemptsService.setCurrentQuestion(request.getAttemptId(), nextQuestion);
            }

            NextQuestion next = new NextQuestion(nextQuestion.getQuestionid(),nextQuestion.getQuestion(),nextQuestion.getOption1(),nextQuestion.getOption2(), nextQuestion.getOption3(),nextQuestion.getOption4());
            QuizAttemptResponse response = new QuizAttemptResponse(next);
            return ResponseEntity.ok(response);
        }
        else {
            if(index + 1 >= quiz.getQuestions().size()) {
                Leaderboard leaderboard = this.attemptsService.getLeaderboardByQuizId(quiz.getQuizid());
                QuizCompletedResponse response = new QuizCompletedResponse(attempt.getScore(), "Incorrect Answer! Quiz Completed",leaderboard);
                return ResponseEntity.ok(response);
            }
            nextQuestion = quiz.getQuestions().get(index+1);
            this.attemptsService.setCurrentQuestion(request.getAttemptId(), nextQuestion);
            QuizAttemptResponse response = new QuizAttemptResponse();
            response.setMessage("Incorrect Answer");
            response.setCorrectAnswer(currentQuestion.getAnswer());
            NextQuestion next = new NextQuestion(nextQuestion.getQuestionid(),nextQuestion.getQuestion(),nextQuestion.getOption1(),nextQuestion.getOption2(), nextQuestion.getOption3(),nextQuestion.getOption4());
            response.setNextQuestion(next);
            return ResponseEntity.ok(response);
        }

    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<?> viewLeaderboardByQuizId(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User myuser = this.customUserDetailsService.findOne(email);

        if(!myuser.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed");
        }

        Leaderboard leaderboard = this.attemptsService.getLeaderboardByQuizId(id);
        return ResponseEntity.ok(leaderboard);
    }
}
