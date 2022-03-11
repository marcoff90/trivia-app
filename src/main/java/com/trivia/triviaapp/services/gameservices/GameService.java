package com.trivia.triviaapp.services.gameservices;

import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.models.game.Game;
import com.trivia.triviaapp.models.game.GameDTO;
import com.trivia.triviaapp.models.game.MultiplayerGame;
import com.trivia.triviaapp.models.game.RandomPlayerGame;

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

  GameDTO getQuestionForTheRound(String shortCode, Integer categoryId, String deviceId, Integer random);

  boolean isCategoryInTheGame(String shortCode, Integer categoryId);

  MultiplayerGame startMultiPlayerGame(String shortCode);

  GameDTO checkAnswer(String shortCode, String deviceId, Integer categoryId, Integer questionId, Integer answerId);

  Game showRoundResults(String shortCode, String deviceId);

  boolean didAllPlayersAnsweredInMultiGame(String shortCode);

  boolean didAllPlayersAnsweredInRandomGame(String shortCode);


}