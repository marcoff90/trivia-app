package com.trivia.triviaapp.services.questionservices;

import com.trivia.triviaapp.factories.MessageFactory;
import com.trivia.triviaapp.models.Message;
import com.trivia.triviaapp.models.PossibleAnswer;
import com.trivia.triviaapp.models.Question;
import com.trivia.triviaapp.repositories.QuestionRepository;
import com.trivia.triviaapp.services.playerservices.PlayerService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  private QuestionRepository questionRepository;
  private Random rand;
  private MessageFactory messageFactory;
  private PlayerService playerService;

  @Autowired
  public QuestionServiceImpl(QuestionRepository questionRepository, MessageFactory messageFactory, PlayerService playerService) {
    this.messageFactory = messageFactory;
    this.playerService = playerService;
    this.rand = new Random();
    this.questionRepository = questionRepository;
  }

  @Override
  public List<Question> findAll() {
    return questionRepository.findAll();
  }

  @Override
  public List<Question> getRandomQuestionsFromCategory(Integer id, Integer random, String deviceId) {
    List<Question> randomQuestions = new ArrayList<>();

    for (int i = 0; i < random; i++) {
      Question q = questionRepository.findByCategoryId(id)
          .get(rand.nextInt(questionRepository.findByCategoryId(id).size()));

      if (!playerService.didUserAnsweredQuestion(deviceId, q.getId())) {
        randomQuestions.add(q);
      } else {
        getRandomQuestionsFromCategory(id, random, deviceId);
      }
    }
    for (Question q : randomQuestions) {
      List<PossibleAnswer> answers = q.getAnswers();
      Collections.shuffle(answers);
      q.setAnswers(answers);
    }
    return randomQuestions;
  }

  @Override
  public Message checkAnswer(Integer id, Integer answerId) {
    if (findById(id).getCorrectAnswerId() == answerId) {
      return messageFactory.getNewMessage("You've answered correctly!");
    } else {
      return messageFactory.getNewMessage("Not a good tip!");
    }
  }

  @Override
  public Question findById(Integer id) {
    return questionRepository.findById(id).orElse(null);
  }
}
