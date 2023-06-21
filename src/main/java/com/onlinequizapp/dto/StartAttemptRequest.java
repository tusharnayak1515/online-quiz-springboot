package com.onlinequizapp.dto;

public class StartAttemptRequest {
    private int quizId;
    private int userId;

    public StartAttemptRequest() {
    }

    public StartAttemptRequest(int quizId, int userId) {
        this.quizId = quizId;
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StartAttemptRequest [quizId=" + quizId + ", userId=" + userId + "]";
    }
    
}
