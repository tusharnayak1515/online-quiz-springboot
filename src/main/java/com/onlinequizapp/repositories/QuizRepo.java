package com.onlinequizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinequizapp.entities.Quiz;

public interface QuizRepo extends JpaRepository<Quiz, Integer> {
	
}
