package com.trivia.triviaapp.repositories;

import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.models.game.MultiplayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiplayerGameRepository extends JpaRepository<MultiplayerGame, Integer> {

  @Query("SELECT g FROM multiplayer_games g WHERE g.shortCode = ?1 AND g.isFinished = false AND g.hasStarted = false")
  MultiplayerGame findByShortCodeUnfinishedNotStarted(String shortCode);

  @Query("SELECT g FROM multiplayer_games g WHERE g.hostPlayer = ?1 AND g.isFinished = false AND g.hasStarted = false")
  MultiplayerGame findByHostPlayerUnfinished(Player player);

  @Query("SELECT g FROM multiplayer_games g WHERE g.shortCode = ?1 AND g.isFinished = false AND g.hasStarted = true")
  MultiplayerGame findByShortCodeNotFinishedStarted(String shortCode);

}
