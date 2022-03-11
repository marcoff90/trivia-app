package com.trivia.triviaapp.controllers;

import com.trivia.triviaapp.factories.ErrorFactory;
import com.trivia.triviaapp.services.playerservices.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

  private PlayerService playerService;
  private ErrorFactory errorFactory;

  @Autowired
  public PlayerController(PlayerService playerService, ErrorFactory errorFactory) {
    this.playerService = playerService;
    this.errorFactory = errorFactory;
  }

  public ResponseEntity<Object> showWelcomePlayer(String deviceId) {
    if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));
    } else if (playerService.findByDeviceId(deviceId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Player not found!"));
    } else {
      return ResponseEntity.ok().body(playerService.findByDeviceId(deviceId));
    }
  }

  public ResponseEntity<Object> storePlayer(String username, String deviceId) {
    System.out.println(deviceId);
    if (deviceId == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Device id missing!"));
    } else if (username == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Username must be entered!"));
    } else if (playerService.findByDeviceId(deviceId) != null && playerService.findByUsername(username) == playerService.findByDeviceId(
        deviceId)) {
      return showWelcomePlayer(deviceId);
    } else if (playerService.findByDeviceId(deviceId) != null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorFactory.getNewErrorMessage("Device already registered"));
    } else {
      playerService.addPlayer(username, deviceId);
      return showWelcomePlayer(deviceId);
    }
  }
}
