package com.trivia.triviaapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PossibleAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String answer;
  @ManyToOne
  @JsonIgnore
  private Question question;

  public PossibleAnswer() {
  }
}
