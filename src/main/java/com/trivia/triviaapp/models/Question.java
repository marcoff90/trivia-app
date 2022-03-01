package com.trivia.triviaapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity(name = "questions")
@Data
@JsonPropertyOrder({"id", "type", "difficulty", "correctAnswerId", "correctAnswer", "question", "answers", "category"})
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String type;
  @Column(unique = true, length = 2000)
  private String question;
  private String difficulty;
  private String correctAnswer;
  @ManyToOne
  private Category category;
  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private List<PossibleAnswer> answers;
  private int correctAnswerId;

  public Question() {
  }
}
