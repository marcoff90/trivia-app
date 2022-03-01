package com.trivia.triviaapp.models;

import lombok.Data;

@Data
public class ErrorMessage {

  private String error;

  public ErrorMessage(String error) {
    this.error = error;
  }
}
