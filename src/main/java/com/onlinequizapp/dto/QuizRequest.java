package com.onlinequizapp.dto;

import java.util.ArrayList;
import java.util.List;

import com.onlinequizapp.entities.Question;

public class QuizRequest {
    private String title;
    private List<Integer> questions = new ArrayList<>();
    
    public QuizRequest() {
    }
    
    public QuizRequest(String title, List<Integer> questions) {
        this.title = title;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<Integer> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Integer> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "QuizRequest [title=" + title + ", questions=" + questions + "]";
    }
    
}
