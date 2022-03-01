package com.trivia.triviaapp.models;

import lombok.Data;

@Data
public class Message {

  private String message;

  public Message(String message) {
    this.message = message;
  }
}
