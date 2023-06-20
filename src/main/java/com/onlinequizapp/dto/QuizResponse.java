package com.onlinequizapp.dto;

import com.onlinequizapp.entities.Quiz;

public class QuizResponse {
	private boolean success;
	private String error;
	private Quiz quiz;
	private Iterable<Quiz> quizes;
	
	public QuizResponse() {
		super();
	}
	public QuizResponse(boolean success, String error, Quiz quiz, Iterable<Quiz> quizes) {
		super();
		this.success = success;
		this.error = error;
		this.quiz = quiz;
		this.quizes = quizes;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public Iterable<Quiz> getQuizes() {
		return quizes;
	}
	public void setQuizes(Iterable<Quiz> quizes) {
		this.quizes = quizes;
	}
	@Override
	public String toString() {
		return "QuizResponse [success=" + success + ", error=" + error + ", quiz=" + quiz + ", quizes=" + quizes + "]";
	}
	
}
