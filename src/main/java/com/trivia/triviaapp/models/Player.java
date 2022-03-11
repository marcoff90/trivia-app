package com.trivia.triviaapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"id", "deviceId", "username", "active", "totalScore", "numberOfAnsweredQuestionInOneGame", "didAnswerQuestionCorrectly",
    "currentGameScore", "currentQuestion"})
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(unique = true)
  private String deviceId;
  private String username;
  @OneToOne
  private Question currentQuestion;
  private int totalScore;
  private int currentGameScore;
  private int numberOfAnsweredQuestionInOneGame;
  private Boolean didAnswerQuestionCorrectly;
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

}
