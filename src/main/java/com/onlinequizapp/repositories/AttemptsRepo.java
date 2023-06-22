package com.onlinequizapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlinequizapp.dto.LeaderboardResponse;
import com.onlinequizapp.entities.Attempt;

public interface AttemptsRepo extends JpaRepository<Attempt, Integer> {
     @Query("SELECT NEW com.onlinequizapp.dto.LeaderboardResponse(u.name, MAX(a.score)) " +
           "FROM Attempt a " +
           "RIGHT JOIN a.user u " +
           "WHERE a.quiz.id = :quizId " +
           "GROUP BY u.id " +
           "ORDER BY MAX(a.score) DESC")
    public List<LeaderboardResponse> getLeaderBoardByQuiz(@Param("quizId") int quizId);
}
