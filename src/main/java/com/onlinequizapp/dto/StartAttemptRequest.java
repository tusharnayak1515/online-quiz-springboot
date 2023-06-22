package com.onlinequizapp.dto;

public class StartAttemptRequest {
    private int quizId;

    public StartAttemptRequest() {
    }

    public StartAttemptRequest(int quizId) {
        this.quizId = quizId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "StartAttemptRequest [quizId=" + quizId + "]";
    }
    
}
