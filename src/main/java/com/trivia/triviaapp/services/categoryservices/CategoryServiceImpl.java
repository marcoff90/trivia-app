package com.trivia.triviaapp.services.categoryservices;

import com.trivia.triviaapp.models.Category;
import com.trivia.triviaapp.repositories.CategoryRepository;
import com.trivia.triviaapp.repositories.PossibleAnswerRepository;
import com.trivia.triviaapp.repositories.QuestionRepository;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private CategoryRepository categoryRepository;
  private PossibleAnswerRepository answerRepository;
  private QuestionRepository questionRepository;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository,
      PossibleAnswerRepository answerRepository, QuestionRepository questionRepository) {
    this.categoryRepository = categoryRepository;
    this.answerRepository = answerRepository;
    this.questionRepository = questionRepository;
  }

  @Override
  public List<Category> getSixRandomCategories() {
    Random random = new Random();
    List<Category> categories = categoryRepository.findAll();
    Collections.shuffle(categories, random);
    return categories.subList(0, 6);
  }
}
