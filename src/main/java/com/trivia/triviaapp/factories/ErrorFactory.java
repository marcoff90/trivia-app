package com.trivia.triviaapp.factories;

import com.trivia.triviaapp.models.ErrorMessage;
import org.springframework.stereotype.Component;

@Component
public class ErrorFactory {

  public ErrorMessage getNewErrorMessage(String errorMessage) {
    return new ErrorMessage(errorMessage);
  }
}
