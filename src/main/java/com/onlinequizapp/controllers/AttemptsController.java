package com.onlinequizapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinequizapp.dto.NextQuestion;
import com.onlinequizapp.dto.QuizAttemptResponse;
import com.onlinequizapp.dto.StartAttemptRequest;
import com.onlinequizapp.dto.SubmitAnswerRequest;
import com.onlinequizapp.entities.Attempt;
import com.onlinequizapp.entities.Question;
import com.onlinequizapp.entities.Quiz;
import com.onlinequizapp.services.AttemptsService;
import com.onlinequizapp.services.QuizService;

@RestController
@RequestMapping("/api/attempts")
public class AttemptsController {
    
    @Autowired
	private QuizService quizService;

	@Autowired
	private AttemptsService attemptsService;

    @PostMapping("/start")
    public ResponseEntity<?> startAttempt(@RequestBody StartAttemptRequest request) {
        Question question = this.quizService.startAttempt(request.getQuizId(), request.getUserId());
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
                this.attemptsService.update(attempt);
                return ResponseEntity.ok("Quiz is completed");
            }
            else {
                attempt.setScore(attempt.getScore() + 1);
                this.attemptsService.update(attempt);
                nextQuestion = quiz.getQuestions().get(index+1);
                this.attemptsService.setCurrentQuestion(request.getAttemptId(), nextQuestion);
            }

            NextQuestion next = new NextQuestion(nextQuestion.getQuestionid(),nextQuestion.getQuestion(),nextQuestion.getOption1(),nextQuestion.getOption2(), nextQuestion.getOption3(),nextQuestion.getOption4());
            QuizAttemptResponse response = new QuizAttemptResponse(next);
            return ResponseEntity.ok(response);
        }
        else {
            if(index + 1 >= quiz.getQuestions().size()) {
                QuizAttemptResponse response = new QuizAttemptResponse();
                response.setMessage("Incorrect Answer! Quiz Completed");
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
}
