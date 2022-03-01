package com.trivia.triviaapp.factories;

import com.trivia.triviaapp.models.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

  public Message getNewMessage(String message) {
    return new Message(message);
  }
}
