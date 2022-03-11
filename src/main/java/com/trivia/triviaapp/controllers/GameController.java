package com.trivia.triviaapp.controllers;

import com.trivia.triviaapp.factories.ErrorFactory;
import com.trivia.triviaapp.models.game.MultiplayerGame;
import com.trivia.triviaapp.models.game.RandomPlayerGame;
import com.trivia.triviaapp.services.gameservices.GameService;
import com.trivia.triviaapp.services.playerservices.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

  private GameService gameService;
  private ErrorFactory errorFactory;
  private PlayerService playerService;

  @Autowired
  public GameController(GameService gameService, ErrorFactory errorFactory,
      PlayerService playerService) {
    this.gameService = gameService;
    this.errorFactory = errorFactory;
    this.playerService = playerService;
  }

  public ResponseEntity<Object> storeMultiPlayerGame(String deviceId) {
    if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));
    } else if (playerService.findByDeviceId(deviceId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Player not found"));
    } else {
      return ResponseEntity.ok().body(gameService.addOrFindMultiplayerGame(deviceId));
    }
  }

  public ResponseEntity<Object> storeRandomPlayerGame(String deviceId) {
    if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));
    } else if (playerService.findByDeviceId(deviceId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Player not found"));
    } else if (gameService.findByHostPlayer(playerService.findByDeviceId(deviceId)) != null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorFactory.getNewErrorMessage("Joining your own game is not possible!"));
    } else {
      RandomPlayerGame game = (RandomPlayerGame) gameService.addOrFindRandomPlayerGame(deviceId);
      if (game.isHasStarted()) {
        return showCategories(game.getShortCode(), deviceId);
      } else {
        return ResponseEntity.ok().body(game);
      }
    }
  }

  public ResponseEntity<Object> joinGame(String gameCode, String deviceId) {
    if (gameCode == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Game code must be send!"));

    } else if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));

    } else if (gameService.findMultiplayerGameByShortCodeNotFinishedNotStarted(gameCode) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Game not found"));

    } else if (playerService.findByDeviceId(deviceId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Player not found"));

    } else if (gameService.findMultiplayerGameByShortCodeNotFinishedNotStarted(gameCode).getJoiningPlayers().size() > 5) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorFactory.getNewErrorMessage("The game is full!"));

    } else if (gameService.findMultiplayerGameByShortCodeNotFinishedNotStarted(gameCode).getHostPlayer() == playerService.findByDeviceId(
        deviceId)) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Joining your own game is not possible!"));

    } else {
      return ResponseEntity.ok().body(gameService.joinMultiplayerGame(gameCode, deviceId));
    }
  }

  public ResponseEntity<Object> showCategories(String gameCode, String deviceId) {
    RandomPlayerGame randomGame = gameService.findByRandomPlayerGameByShortCode(gameCode);
    MultiplayerGame multiGame = gameService.findByMultiplayerGameByShortCode(gameCode);

    if (gameCode == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Game code must be send!"));

    } else if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));

    } else if (playerService.findByDeviceId(deviceId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Player not found"));

    } else if (randomGame == null && multiGame == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Game not found"));

    } else if (!gameService.isPlayerInTheGame(gameCode, deviceId)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorFactory.getNewErrorMessage("Player not in the game!"));

    } else {
      return ResponseEntity.ok().body(gameService.showGameCategories(gameCode));
    }
  }

  public ResponseEntity<Object> getQuestions(String gameCode, Integer categoryId, String deviceId, Integer random) {
    RandomPlayerGame randomGame = gameService.findByRandomPlayerGameByShortCode(gameCode);
    MultiplayerGame multiGame = gameService.findByMultiplayerGameByShortCode(gameCode);

    if (gameCode == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Game code must be send!"));

    } else if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));

    } else if (random == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Number of questions is needed!"));

    } else if (randomGame == null && multiGame == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Game not found"));

    } else if (!gameService.isCategoryInTheGame(gameCode, categoryId)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Category doesn't exist!"));

    } else if (playerService.findByDeviceId(deviceId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Player not found"));

    } else if (!gameService.isPlayerInTheGame(gameCode, deviceId)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorFactory.getNewErrorMessage("Player not in the game!"));

    } else {
      return ResponseEntity.ok().body(gameService.getQuestionForTheRound(gameCode, categoryId, deviceId, random));
    }
  }

  public ResponseEntity<Object> startMultiPlayerGame(String gameCode, String deviceId) {
    MultiplayerGame game = gameService.findMultiplayerGameByShortCodeNotFinishedNotStarted(gameCode);

    if (gameCode == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Game code must be send!"));

    } else if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));

    } else if (game == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Game not found"));

    } else if (playerService.findByDeviceId(deviceId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Player not found"));

    } else {
      int a = 2+2;
      return ResponseEntity.ok().body(gameService.startMultiPlayerGame(gameCode));
    }
  }

  public ResponseEntity<Object> checkAnswer(String gameCode, String deviceId, Integer categoryId, Integer questionId, Integer answerId) {
    RandomPlayerGame randomGame = gameService.findByRandomPlayerGameByShortCode(gameCode);
    MultiplayerGame multiGame = gameService.findByMultiplayerGameByShortCode(gameCode);

    if (gameCode == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Game code must be send!"));

    } else if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));

    } else if (answerId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Tip must be provided!"));

    } else if (questionId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Question id is missing!"));

    } else if (randomGame == null && multiGame == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Game not found"));

    } else if (!gameService.isCategoryInTheGame(gameCode, categoryId)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Category doesn't exist!"));

    } else if (playerService.findByDeviceId(deviceId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Player not found"));

    } else if (!gameService.isPlayerInTheGame(gameCode, deviceId)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorFactory.getNewErrorMessage("Player not in the game!"));

    } else {
      return ResponseEntity.status(HttpStatus.OK).body(gameService.checkAnswer(gameCode, deviceId, categoryId, questionId, answerId));
    }
  }

  public ResponseEntity<Object> showResults(String gameCode, String deviceId) {
    RandomPlayerGame randomGame = gameService.findByRandomPlayerGameByShortCode(gameCode);
    MultiplayerGame multiGame = gameService.findByMultiplayerGameByShortCode(gameCode);

    if (gameCode == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Game code must be send!"));

    } else if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));

    } else if (randomGame == null && !gameService.didAllPlayersAnsweredInMultiGame(gameCode)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(errorFactory.getNewErrorMessage("Waiting for other players to answer"));

    } else if (multiGame == null && !gameService.didAllPlayersAnsweredInRandomGame(gameCode)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(errorFactory.getNewErrorMessage("Waiting for other player to answer"));

    } else {
      return ResponseEntity.ok().body(gameService.showRoundResults(gameCode, deviceId));
    }
  }
}
