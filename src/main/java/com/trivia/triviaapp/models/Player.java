package com.trivia.triviaapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trivia.triviaapp.models.game.Game;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity(name = "players")
@Data
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(unique = true)
  private String deviceId;
  private String username;
  @OneToOne
  private Question currentQuestion;
//  private int totalScore;
//  private int currentGameScore;
  @OneToMany
  @JsonIgnore
  private List<Question> answeredQuestions;
  private boolean isActive;

  public Player(String username, String deviceId) {
    this.deviceId = deviceId;
    this.username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
    this.isActive = false;
  }

  public Player() {
    this.isActive = false;
  }

  /**
   *  private int id;
   *  private String deviceId - generated from react native
   *  private String username
   *  private int totalScore
   *  private int currentGameScore
   *  private List<Question> answeredQuestions
   *  private boolean isActive = when app starts to true, when closed to false
   *
   *  ? private Game currentGame
   *  ? private List<Game> historyOfGames
   *  ? private boolean isChoosingCategory
   */



}
