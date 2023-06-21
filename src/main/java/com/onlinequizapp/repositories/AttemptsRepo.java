package com.onlinequizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinequizapp.entities.Attempt;

public interface AttemptsRepo extends JpaRepository<Attempt, Integer> {
    
}
