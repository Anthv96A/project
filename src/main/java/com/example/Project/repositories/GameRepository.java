package com.example.Project.repositories;

import com.example.Project.domain.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game,Long> {

    Optional<Game> findById(Long id);

    @Query(value = "SELECT gl.name, count(g.id) AS Games FROM goal gl " +
            "INNER JOIN goal_game gg " +
            "ON gl.id = gg.goal_id " +
            "INNER JOIN game g " +
            "ON gg.game_id = g.id " +
            "GROUP BY gl.name " +
            "ORDER BY Games DESC " +
            "LIMIT 1"
            , nativeQuery = true)
    List<Object[]> calculateMostFrequentGoal();

    @Query(value = "SELECT gl.name, count(g.id) AS Games FROM goal gl " +
            "INNER JOIN goal_game gg " +
            "ON gl.id = gg.goal_id " +
            "INNER JOIN game g " +
            "ON gg.game_id = g.id " +
            "GROUP BY gl.name " +
            "ORDER BY Games ASC " +
            "LIMIT 1"
            , nativeQuery = true)
    List<Object[]> calculateLeastFrequentGoal();


    @Query(value = "SELECT gl.name, SUM(g.total_score) AS HighestScore from goal gl " +
            "INNER JOIN goal_game gg " +
            "ON gl.id = gg.goal_id " +
            "INNER JOIN game g " +
            "ON gg.game_id = g.id " +
            "GROUP BY gl.name " +
            "ORDER BY HighestScore DESC " +
            "LIMIT 1 ", nativeQuery = true)
    List<Object[]> calculateHighestScore();

    @Query(value = "SELECT gl.name, SUM(g.total_score) AS LowestScore from goal gl " +
            "INNER JOIN goal_game gg " +
            "ON gl.id = gg.goal_id " +
            "INNER JOIN game g " +
            "ON gg.game_id = g.id " +
            "GROUP BY gl.name " +
            "ORDER BY LowestScore ASC " +
            "LIMIT 1 ", nativeQuery = true)
    List<Object[]> calculateLowestScore();

    @Query(value = "SELECT gl.name, count(g.id) AS Games FROM goal gl " +
            "INNER JOIN goal_game gg " +
            "ON gl.id = gg.goal_id " +
            "INNER JOIN game g " +
            "ON gg.game_id = g.id " +
            "GROUP BY gl.name " +
            "ORDER BY Games DESC "
            , nativeQuery = true)
    List<Object[]> allGoalsAndGameCount();


    @Query(value = "SELECT * FROM game g " +
            "INNER JOIN goal_game gg ON g.id = gg.game_id " +
            "INNER JOIN goal gl ON gg.goal_id = gl.id " +
            "WHERE gl.name = :name " +
            "ORDER BY g.id DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Game> getLastGameByGoal(@Param("name") String name);


    List<Game> findGameByDatePlayedBetweenOrderByDatePlayedDesc(Date from, Date after);


    @Query(value = "SELECT g.name, count(g.id) AS Games FROM game g " +
            "WHERE g.date_played BETWEEN :from AND :to " +
            "GROUP BY g.name " +
            "ORDER BY Games DESC " +
            "LIMIT 1"
        , nativeQuery = true)
    List<Object[]> calculateMostFrequentGoalForPeriod(@Param("from") Date from, @Param("to") Date to);

    @Query(value = "SELECT g.name, count(g.id) AS Games FROM game g " +
            "WHERE g.date_played BETWEEN :from AND :to " +
            "GROUP BY g.name " +
            "ORDER BY Games ASC " +
            "LIMIT 1"
            , nativeQuery = true)
    List<Object[]> calculateLeastFrequentGoalForPeriod(@Param("from") Date from, @Param("to") Date to);

    @Query(value = "SELECT g.name, sum(g.total_score) AS Games FROM game g " +
            "WHERE g.date_played BETWEEN :from AND :to " +
            "GROUP BY g.name " +
            "ORDER BY Games DESC " +
            "LIMIT 1"
            , nativeQuery = true)
    List<Object[]> calculateHighestScoredGoalForPeriod(@Param("from") Date from, @Param("to") Date to);

    @Query(value = "SELECT g.name, sum(g.total_score) AS Games FROM game g " +
            "WHERE g.date_played BETWEEN :from AND :to " +
            "GROUP BY g.name " +
            "ORDER BY Games ASC " +
            "LIMIT 1"
            , nativeQuery = true)
    List<Object[]> calculateLowestScoredGoalForPeriod(@Param("from") Date from, @Param("to") Date to);


    @Query(value = "SELECT g.name, count(g.id) AS Games FROM game g " +
            "WHERE g.date_played BETWEEN :from AND :to " +
            "GROUP BY g.name " +
            "ORDER BY Games DESC "
            , nativeQuery = true)
    List<Object[]> timePeriodGoalsAndGameCount(@Param("from") Date from, @Param("to") Date to);

    long countGamesByDatePlayedBetween(Date from, Date to);




}

