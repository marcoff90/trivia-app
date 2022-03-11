package com.trivia.triviaapp.services.categoryservices;

import com.trivia.triviaapp.models.Category;
import com.trivia.triviaapp.repositories.CategoryRepository;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private CategoryRepository categoryRepository;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<Category> getSixRandomCategories() {
    Random random = new Random();
    List<Category> categories = categoryRepository.findAll();
    Collections.shuffle(categories, random);
    return categories.subList(0, 6);
  }
}
