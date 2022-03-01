package com.trivia.triviaapp.services.playerservices;

import com.trivia.triviaapp.models.Player;

public interface PlayerService {

  Player findByDeviceId(String deviceId);

  Player findByUsername(String username);

  void addPlayer(String username, String deviceId);

  boolean didUserAnsweredQuestion(String deviceId, Integer questionId);

}
