package com.onlinequizapp.dto;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    private int quizId;
    private List<LeaderboardResponse> attempts = new ArrayList<LeaderboardResponse>();

    public Leaderboard() {
    }

    public Leaderboard(int quizId, List<LeaderboardResponse> attempts) {
        this.quizId = quizId;
        this.attempts = attempts;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<LeaderboardResponse> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<LeaderboardResponse> attempts) {
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "Leaderboard [quizId=" + quizId + ", attempts=" + attempts + "]";
    }
    
}
