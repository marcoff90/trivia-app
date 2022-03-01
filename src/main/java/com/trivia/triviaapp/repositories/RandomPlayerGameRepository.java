package com.trivia.triviaapp.repositories;

import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.models.game.RandomPlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomPlayerGameRepository extends JpaRepository<RandomPlayerGame, Integer> {

  @Query("SELECT g FROM random_player_games g WHERE g.isFinished = false AND g.hasStarted = false ORDER BY g.shortCode ASC")
  RandomPlayerGame findByFinishedIsFalseOrderByShortCodeAsc();

  @Query("SELECT g FROM random_player_games g WHERE g.hostPlayer = ?1 AND g.isFinished = false AND g.hasStarted = false")
  RandomPlayerGame findByHostPlayer(Player player);

  @Query("SELECT g FROM random_player_games g WHERE g.shortCode = ?1 AND g.isFinished = false AND g.hasStarted = true")
  RandomPlayerGame findByShortCodeNotFinishedStarted(String shortCode);

}
