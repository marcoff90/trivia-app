package com.trivia.triviaapp.repositories;

import com.trivia.triviaapp.models.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

  List<Question> findByCategoryId(Integer id);

}
