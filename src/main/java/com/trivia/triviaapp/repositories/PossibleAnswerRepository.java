package com.trivia.triviaapp.repositories;

import com.trivia.triviaapp.models.PossibleAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossibleAnswerRepository extends JpaRepository<PossibleAnswer, Integer> {

  PossibleAnswer findByQuestionId(Integer id);

}
