package com.onlinequizapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlinequizapp.dto.LeaderboardResponse;
import com.onlinequizapp.entities.Attempt;
import com.onlinequizapp.entities.Quiz;

public interface AttemptsRepo extends JpaRepository<Attempt, Integer> {
    // @Query("SELECT User.name, max(Attempt.score) as score from Attempt right join User on Attempt.user = User.id where Attempt.quiz = :quizId group by Attempt.user")
     @Query("SELECT NEW com.onlinequizapp.dto.LeaderboardResponse(u.name, MAX(le.score)) " +
           "FROM Attempt le " +
           "RIGHT JOIN le.user u " +
           "WHERE le.quiz.id = :quizId " +
           "GROUP BY u.name")

    // @Query("SELECT u FROM User u left join Attempt a on a.user = u.id where a.quiz = :quizId")
    // @Query("SELECT u FROM User u JOIN u.name r ")
    public List<LeaderboardResponse> getLeaderBoardByQuiz(@Param("quizId") int quizId);
}
