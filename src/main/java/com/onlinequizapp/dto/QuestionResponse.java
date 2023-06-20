package com.onlinequizapp.dto;

import java.util.List;

import com.onlinequizapp.entities.Question;

public class QuestionResponse {
	private boolean success;
	private String error;
	private Question question;
	private List<Question> questions;
	
	public QuestionResponse() {
		super();
	}
	
	public QuestionResponse(boolean success, String error, Question question, List<Question> questions) {
		super();
		this.success = success;
		this.error = error;
		this.question = question;
		this.questions = questions;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
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
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "QuestionResponse [success=" + success + ", error=" + error + ", question=" + question + ", questions="
				+ questions + "]";
	}
	
}
