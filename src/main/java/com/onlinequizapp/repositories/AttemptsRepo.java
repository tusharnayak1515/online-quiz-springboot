package com.onlinequizapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlinequizapp.dto.LeaderboardResponse;
import com.onlinequizapp.entities.Attempt;

public interface AttemptsRepo extends JpaRepository<Attempt, Integer> {
     @Query("SELECT NEW com.onlinequizapp.dto.LeaderboardResponse(u.name, MAX(le.score)) " +
           "FROM Attempt le " +
           "RIGHT JOIN le.user u " +
           "WHERE le.quiz.id = :quizId " +
           "GROUP BY u.name")
    public List<LeaderboardResponse> getLeaderBoardByQuiz(@Param("quizId") int quizId);
}
