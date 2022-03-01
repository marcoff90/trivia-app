package com.trivia.triviaapp.services.questionservices;

import com.trivia.triviaapp.models.Message;
import com.trivia.triviaapp.models.Question;
import java.util.List;

public interface QuestionService {

  List<Question> findAll();

  List<Question> getRandomQuestionsFromCategory(Integer id, Integer random, String deviceId);

  Message checkAnswer(Integer id, Integer answerId);

  Question findById(Integer id);

}
