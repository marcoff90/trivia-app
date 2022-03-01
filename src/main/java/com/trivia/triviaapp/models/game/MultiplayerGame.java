package com.trivia.triviaapp.models.game;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trivia.triviaapp.models.Player;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "multiplayer_games")
@Data
@JsonPropertyOrder({"id", "shortCode", "hasStarted", "finished", "roundNumber", "hashCode", "currentRoundCategories", "hostPlayer", "joiningPlayers"})
public class MultiplayerGame extends Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  protected Player hostPlayer;
  @ManyToMany
  protected List<Player> joiningPlayers;

}
