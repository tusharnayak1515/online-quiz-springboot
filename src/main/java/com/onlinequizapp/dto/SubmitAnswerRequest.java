package com.onlinequizapp.dto;

public class SubmitAnswerRequest {
    private int attemptId;
    private int questionId;
    private String answer;
    
    public SubmitAnswerRequest() {
    }

    public SubmitAnswerRequest(int attemptId, int questionId, String answer) {
        this.attemptId = attemptId;
        this.questionId = questionId;
        this.answer = answer;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "SubmitAnswerRequest [attemptId=" + attemptId + ", questionId=" + questionId + ", answer=" + answer
                + "]";
    }
    
}
