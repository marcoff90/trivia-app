package com.trivia.triviaapp.repositories;

import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

  Player findByDeviceId(String deviceId);

  Player findByUsername(String userName);

  @Query(value = "SELECT answered_questions_id FROM players_answered_questions WHERE answered_questions_id = ?1 AND players_id = ?2", nativeQuery = true)
  Question findByQuestionId(Integer id, Integer playersId);


}
