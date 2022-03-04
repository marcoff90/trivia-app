package com.trivia.triviaapp.models.game;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trivia.triviaapp.models.Category;
import com.trivia.triviaapp.models.Player;
import java.util.List;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "shortCode", "hasStarted", "finished", "roundNumber", "hashcode", "currentRoundCategories", "requestSendingPlayer"})
public class GameDTO {

  private int id;
  private String shortCode;
  private boolean hasStarted;
  private boolean isFinished;
  private int roundNumber;
  private String hashcode;
  private List<Category> currentRoundCategories;
  private Player requestSendingPlayer;

  public GameDTO(MultiplayerGame game, Player player) {
    this.id = game.getId();
    this.shortCode = game.getShortCode();
    this.hasStarted = game.isHasStarted();
    this.isFinished = game.isFinished();
    this.roundNumber = game.getRoundNumber();
    this.hashcode = game.getHashcode();
    this.currentRoundCategories = game.getCurrentRoundCategories();
    this.requestSendingPlayer = player;
  }

  public GameDTO(RandomPlayerGame game, Player player) {
    this.id = game.getId();
    this.shortCode = game.getShortCode();
    this.hasStarted = game.isHasStarted();
    this.isFinished = game.isFinished();
    this.roundNumber = game.getRoundNumber();
    this.hashcode = game.getHashcode();
    this.currentRoundCategories = game.getCurrentRoundCategories();
    this.requestSendingPlayer = player;
  }


}
