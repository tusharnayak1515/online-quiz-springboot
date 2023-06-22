package com.onlinequizapp.dto;

public class SubmitAnswerRequest {
    private int attemptId;
    private String answer;
    
    public SubmitAnswerRequest() {
    }

    public SubmitAnswerRequest(int attemptId, String answer) {
        this.attemptId = attemptId;
        this.answer = answer;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "SubmitAnswerRequest [attemptId=" + attemptId + ", answer=" + answer
                + "]";
    }
    
}
