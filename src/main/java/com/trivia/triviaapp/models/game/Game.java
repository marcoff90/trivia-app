package com.trivia.triviaapp.models.game;

import com.trivia.triviaapp.models.Category;
import com.trivia.triviaapp.models.Question;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class Game {

  @Column(unique = true)
  private String hashcode;
  @OneToOne
  private Question currentRoundQuestion;
  @ManyToMany
  private List<Category> currentRoundCategories;
  private int roundNumber;
  @Column(unique = true)
  private String shortCode;
  private boolean isFinished;
  private boolean hasStarted;
  private static AtomicLong codeGenerator = new AtomicLong(1001);

  public Game() {
    this.hashcode = generateHashCode();
    this.shortCode = generateShortCode();
    this.isFinished = false;
    this.hasStarted = false;
    this.roundNumber = 1;
  }

  private String generateHashCode() {
    return UUID.randomUUID().toString();
  }

  private String generateShortCode() {
    return String.valueOf(codeGenerator.getAndIncrement());
  }

  /**
   * methods:
   *    generate unique hashcode
   *    generate shortCode
   *
   */

}
