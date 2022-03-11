package com.trivia.triviaapp.services.categoryservices;

import com.trivia.triviaapp.models.Category;
import java.util.List;

public interface CategoryService {

  List<Category> getSixRandomCategories();

}
