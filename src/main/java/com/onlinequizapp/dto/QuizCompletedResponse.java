package com.onlinequizapp.dto;

public class QuizCompletedResponse {
    private int score;
    private String message;
    private Leaderboard leaderboard;

    public QuizCompletedResponse() {
    }

    public QuizCompletedResponse(int score, String message) {
        this.score = score;
        this.message = message;
    }

    public QuizCompletedResponse(int score, String message, Leaderboard leaderboard) {
        this.score = score;
        this.message = message;
        this.leaderboard = leaderboard;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    @Override
    public String toString() {
        return "QuizCompletedResponse [score=" + score + ", message=" + message + ", leaderboard=" + leaderboard + "]";
    }
    
}
