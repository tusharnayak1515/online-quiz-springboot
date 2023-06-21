package com.onlinequizapp.dto;

public class QuizAttemptResponse {
    private NextQuestion nextQuestion;
    private String message;
    private String correctAnswer;

    public QuizAttemptResponse() {
    }

    public QuizAttemptResponse(NextQuestion nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

    public QuizAttemptResponse(NextQuestion nextQuestion, String message, String correctAnswer) {
        this.nextQuestion = nextQuestion;
        this.message = message;
        this.correctAnswer = correctAnswer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public NextQuestion getNextQuestion() {
        return nextQuestion;
    }

    public void setNextQuestion(NextQuestion nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

    @Override
    public String toString() {
        return "QuizAttemptResponse [nextQuestion=" + nextQuestion + ", message=" + message + ", correctAnswer="
                + correctAnswer + "]";
    }
    
}
