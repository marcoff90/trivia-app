package com.trivia.triviaapp.models.game;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trivia.triviaapp.models.Player;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "random_player_games")
@Data
@JsonPropertyOrder({"id"})
public class RandomPlayerGame extends Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  private Player hostPlayer;
  @ManyToOne
  private Player joiningPlayer;

}
