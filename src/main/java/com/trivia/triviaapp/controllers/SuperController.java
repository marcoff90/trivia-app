package com.trivia.triviaapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trivia/api")
@CrossOrigin(origins = "http://localhost:19002")
public class SuperController {

  private PlayerController playerController;
  private GameController gameController;

  @Autowired
  public SuperController(PlayerController playerController, GameController gameController) {
    this.playerController = playerController;
    this.gameController = gameController;
  }

  @GetMapping("/players")
  public ResponseEntity<Object> showWelcomePlayer(@RequestHeader(required = false) String deviceId) {
    return playerController.showWelcomePlayer(deviceId);
  }

  @PostMapping("/players")
  public ResponseEntity<Object> storePlayer(@RequestParam(required = false) String username,
      @RequestHeader(required = false) String deviceId) {
    return playerController.storePlayer(username, deviceId);
  }

  @PostMapping("/games/multiplayer")
  public ResponseEntity<Object> storeMultiPlayerGame(@RequestHeader(required = false) String deviceId) {
    return gameController.storeMultiPlayerGame(deviceId);
  }

  @PostMapping("/games/random-player")
  public ResponseEntity<Object> storeRandomPlayerGame(@RequestHeader(required = false) String deviceId) {
    return gameController.storeRandomPlayerGame(deviceId);
  }

  @PostMapping("/games/{gameCode}")
  public ResponseEntity<Object> joinGame(@PathVariable(required = false) String gameCode,
      @RequestHeader(required = false) String deviceId) {
    return gameController.joinGame(gameCode, deviceId);
  }

  @GetMapping("/games/{gameCode}/categories")
  public ResponseEntity<Object> showCategories(@PathVariable(required = false) String gameCode,
      @RequestHeader(required = false) String deviceId) {
    return gameController.showCategories(gameCode, deviceId);
  }

  @GetMapping("/games/{gameCode}/categories/{categoryId}/questions")
  public ResponseEntity<Object> getQuestions(@PathVariable(required = false) String gameCode,
      @PathVariable(required = false) Integer categoryId,
      @RequestHeader(required = false) String deviceId,
      @RequestParam(required = false) Integer random) {
    return gameController.getQuestions(gameCode, categoryId, deviceId, random);
  }

  @PutMapping("/games/{gameCode}")
  public ResponseEntity<Object> startMultiPlayerGame(@PathVariable(required = false) String gameCode,
      @RequestHeader(required = false) String deviceId) {
    return gameController.startMultiPlayerGame(gameCode, deviceId);
  }

  @GetMapping("/games/{gameCode}/categories/{categoryId}/questions/{questionId}")
  public ResponseEntity<Object> checkAnswer(@PathVariable(required = false) String gameCode,
      @PathVariable(required = false) Integer categoryId,
      @PathVariable(required = false) Integer questionId, @RequestHeader(required = false) String deviceId,
      @RequestParam(required = false) Integer answerId) {
    return gameController.checkAnswer(gameCode, deviceId, categoryId, questionId, answerId);
  }

  @GetMapping("/games/{gameCode}/players")
  public ResponseEntity<Object> showResults(@PathVariable(required = false) String gameCode,
      @RequestHeader(required = false) String deviceId) {
    return gameController.showResults(gameCode, deviceId);
  }
}
