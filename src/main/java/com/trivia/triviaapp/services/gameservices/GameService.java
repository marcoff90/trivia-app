package com.trivia.triviaapp.services.gameservices;

import com.trivia.triviaapp.models.Category;
import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.models.game.Game;
import com.trivia.triviaapp.models.game.MultiplayerGame;
import com.trivia.triviaapp.models.game.RandomPlayerGame;
import java.util.List;

public interface GameService {

  Game addOrFindMultiplayerGame(String playerDeviceId);

  MultiplayerGame findMultiplayerGameByShortCodeNotFinishedNotStarted(String shortCode);

  RandomPlayerGame findByRandomPlayerGameByShortCode(String shortCode);

  Game addOrFindRandomPlayerGame(String playerDeviceId);

  RandomPlayerGame findByHostPlayer(Player player);

  Game joinMultiplayerGame(String shortCode, String playerDeviceId);

  Game showGameCategories(String shortCode);

  MultiplayerGame findByMultiplayerGameByShortCode(String shortCode);

  boolean isPlayerInTheGame(String shortCode, String playerDeviceId);

  Game getQuestionForTheRound(String shortCode, Integer categoryId, String deviceId, Integer random);

  boolean isCategoryInTheGame(String shortCode, Integer categoryId);

  MultiplayerGame startMultiPlayerGame(String shortCode);

}