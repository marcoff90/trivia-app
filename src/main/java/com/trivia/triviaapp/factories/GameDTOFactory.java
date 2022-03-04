package com.trivia.triviaapp.factories;

import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.models.game.GameDTO;
import com.trivia.triviaapp.models.game.MultiplayerGame;
import com.trivia.triviaapp.models.game.RandomPlayerGame;
import org.springframework.stereotype.Component;

@Component
public class GameDTOFactory {

  public GameDTO getNewGameDTOMultiplayer(MultiplayerGame game, Player player) {
    return new GameDTO(game, player);
  }

  public GameDTO getNewGameDTORandomPlayer(RandomPlayerGame game, Player player) {
    return new GameDTO(game, player);
  }

}
