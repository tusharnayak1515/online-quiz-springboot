package com.onlinequizapp.dto;

public class LeaderboardResponse {
    private String name;
    private int hiscore;

    public LeaderboardResponse() {
    }

    public LeaderboardResponse(String name, int hiscore) {
        this.name = name;
        this.hiscore = hiscore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHiscore() {
        return hiscore;
    }

    public void setHiscore(int hiscore) {
        this.hiscore = hiscore;
    }

    @Override
    public String toString() {
        return "LeaderboardResponse [name=" + name + ", hiscore=" + hiscore + "]";
    }
    
}
