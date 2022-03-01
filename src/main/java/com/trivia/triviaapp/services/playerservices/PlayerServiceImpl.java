package com.trivia.triviaapp.services.playerservices;

import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

  private PlayerRepository playerRepository;

  @Autowired
  public PlayerServiceImpl(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @Override
  public Player findByDeviceId(String deviceId) {
    Player p = playerRepository.findByDeviceId(deviceId);
    if (p != null) {
      p.setActive(true);
      playerRepository.save(p);
    }
    return p;
  }

  @Override
  public Player findByUsername(String username) {
    return playerRepository.findByUsername(username);
  }

  @Override
  public void addPlayer(String username, String deviceId) {
    if (findByDeviceId(deviceId) == null) {
      Player p = new Player(username, deviceId);
      p.setActive(true);
      playerRepository.save(p);
    }
  }

  @Override
  public boolean didUserAnsweredQuestion(String deviceId, Integer questionId) {
    Player p = playerRepository.findByDeviceId(deviceId);
    return playerRepository.findByQuestionId(questionId, p.getId()) != null;
  }
}
