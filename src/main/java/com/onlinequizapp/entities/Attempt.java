package com.onlinequizapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attempts")
public class Attempt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int attemptid;
	
	private int score;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
    @JoinColumn(name = "quiz_id")
	private Quiz quiz;

	@ManyToOne
    @JoinColumn(name = "current_question_id")
    private Question currentQuestion;
	
	public Attempt() {
		super();
	}
	
	public Attempt(int attemptid, int score, User user, Quiz quiz) {
		super();
		this.attemptid = attemptid;
		this.score = score;
		this.user = user;
		this.quiz = quiz;
	}

	public int getAttemptid() {
		return attemptid;
	}

	public void setAttemptid(int attemptid) {
		this.attemptid = attemptid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	
	public Question getCurrentQuestion() {
		return currentQuestion;
	}
	
	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
	
	@Override
	public String toString() {
		return "Attempt [attemptid=" + attemptid + ", score="
				+ score + ", user=" + user + ", quiz=" + quiz + "]";
	}
	
}
