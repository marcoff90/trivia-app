package com.trivia.triviaapp.services.questionservices;

import com.trivia.triviaapp.models.Question;
import java.util.List;

public interface QuestionService {

  List<Question> getRandomQuestionsFromCategory(Integer id, Integer random, String deviceId);

}
