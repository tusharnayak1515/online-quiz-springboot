package com.onlinequizapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attempts")
public class Attempt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int attemptid;
	
	private int score;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Quiz quiz;
	
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

	@Override
	public String toString() {
		return "Attempt [attemptid=" + attemptid + ", score="
				+ score + ", user=" + user + ", quiz=" + quiz + "]";
	}
	
}
