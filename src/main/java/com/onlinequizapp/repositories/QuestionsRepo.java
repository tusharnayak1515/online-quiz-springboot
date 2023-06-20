package com.onlinequizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinequizapp.entities.Question;

public interface QuestionsRepo extends JpaRepository<Question, Integer> {
	
}
