package com.trivia.triviaapp.services.gameservices;

import com.trivia.triviaapp.models.Category;
import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.models.Question;
import com.trivia.triviaapp.models.game.Game;
import com.trivia.triviaapp.models.game.MultiplayerGame;
import com.trivia.triviaapp.models.game.RandomPlayerGame;
import com.trivia.triviaapp.repositories.MultiplayerGameRepository;
import com.trivia.triviaapp.repositories.RandomPlayerGameRepository;
import com.trivia.triviaapp.services.categoryservices.CategoryService;
import com.trivia.triviaapp.services.playerservices.PlayerService;
import com.trivia.triviaapp.services.questionservices.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

  private MultiplayerGameRepository multiplayerGameRepository;
  private PlayerService playerService;
  private CategoryService categoryService;
  private RandomPlayerGameRepository randomPlayerGameRepository;
  private QuestionService questionService;

  @Autowired
  public GameServiceImpl(MultiplayerGameRepository multiplayerGameRepository,
      PlayerService playerService, CategoryService categoryService,
      RandomPlayerGameRepository randomPlayerGameRepository, QuestionService questionService) {
    this.multiplayerGameRepository = multiplayerGameRepository;
    this.playerService = playerService;
    this.categoryService = categoryService;
    this.randomPlayerGameRepository = randomPlayerGameRepository;
    this.questionService = questionService;
  }

  @Override
  public Game addOrFindMultiplayerGame(String playerDeviceId) {
    Player player = playerService.findByDeviceId(playerDeviceId);
    Game notFinishedGame = multiplayerGameRepository.findByHostPlayerUnfinished(player);

    if (notFinishedGame == null) {
      MultiplayerGame game = new MultiplayerGame();
      game.setHostPlayer(player);
      game.setCurrentRoundCategories(categoryService.getSixRandomCategories());
      multiplayerGameRepository.save(game);
      return game;
    } else {
      return notFinishedGame;
    }
  }

  @Override
  public MultiplayerGame findMultiplayerGameByShortCodeNotFinishedNotStarted(String shortCode) {
    return multiplayerGameRepository.findByShortCodeUnfinishedNotStarted(shortCode);
  }

  @Override
  public RandomPlayerGame findByRandomPlayerGameByShortCode(String shortCode) {
    return randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
  }

  @Override
  public Game addOrFindRandomPlayerGame(String playerDeviceId) {
    Player player = playerService.findByDeviceId(playerDeviceId);
    RandomPlayerGame waitingForSecondPlayer = randomPlayerGameRepository.findByFinishedIsFalseOrderByShortCodeAsc();

    if (waitingForSecondPlayer == null) {
      RandomPlayerGame game = new RandomPlayerGame();
      game.setHostPlayer(player);
      game.setCurrentRoundCategories(categoryService.getSixRandomCategories());
      randomPlayerGameRepository.save(game);
      return game;

    } else {

      if (waitingForSecondPlayer.getHostPlayer().isActive()) {
        waitingForSecondPlayer.setJoiningPlayer(player);
        waitingForSecondPlayer.setHasStarted(true);
        randomPlayerGameRepository.save(waitingForSecondPlayer);
      } else {
        waitingForSecondPlayer.setFinished(true);
        randomPlayerGameRepository.save(waitingForSecondPlayer);
        this.addOrFindRandomPlayerGame(playerDeviceId);
      }

      return waitingForSecondPlayer;
    }
  }

  @Override
  public RandomPlayerGame findByHostPlayer(Player player) {
    return randomPlayerGameRepository.findByHostPlayer(player);
  }

  @Override
  public Game joinMultiplayerGame(String shortCode, String playerDeviceId) {
    Player player = playerService.findByDeviceId(playerDeviceId);
    MultiplayerGame game = multiplayerGameRepository.findByShortCodeUnfinishedNotStarted(shortCode);
    game.getJoiningPlayers().add(player);
    multiplayerGameRepository.save(game);
    return game;
  }

  @Override
  public Game showGameCategories(String shortCode) {
    RandomPlayerGame randomGame = randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    MultiplayerGame multiGame = multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);

    if (randomGame == null) {
      return multiGame;
    } else {
      return randomGame;
    }
  }

  @Override
  public MultiplayerGame findByMultiplayerGameByShortCode(String shortCode) {
    return multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
  }

  @Override
  public boolean isPlayerInTheGame(String shortCode, String playerDeviceId) {
    RandomPlayerGame randomGame = randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    MultiplayerGame multiGame = multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    boolean isPlayerInTheGame = false;
    if (randomGame == null) {
      if (multiGame.getHostPlayer().getDeviceId().equals(playerDeviceId)) {
        isPlayerInTheGame = true;
      }
      for (Player p : multiGame.getJoiningPlayers()) {
        if (p.getDeviceId().equals(playerDeviceId)) {
          isPlayerInTheGame = true;
          break;
        }
      }
    } else {
      if (randomGame.getHostPlayer().getDeviceId().equals(playerDeviceId)) {
        isPlayerInTheGame = true;
      } else if (randomGame.getJoiningPlayer().getDeviceId().equals(playerDeviceId)) {
        isPlayerInTheGame = true;
      }
    }
    return isPlayerInTheGame;
  }

  @Override
  public Game getQuestionForTheRound(String shortCode, Integer categoryId, String deviceId, Integer random) {
    RandomPlayerGame randomGame = randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    MultiplayerGame multiGame = multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    Question q = questionService.getRandomQuestionsFromCategory(categoryId, random, deviceId).get(0);

    if (randomGame == null) {
      multiGame.setCurrentRoundQuestion(q);
      multiplayerGameRepository.save(multiGame);
      return multiGame;
    } else {
      randomGame.setCurrentRoundQuestion(q);
      randomPlayerGameRepository.save(randomGame);
      return randomGame;
    }
  }

  @Override
  public boolean isCategoryInTheGame(String shortCode, Integer categoryId) {
    RandomPlayerGame randomGame = randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    MultiplayerGame multiGame = multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    boolean isCategoryInTheGame = false;
    if (randomGame == null) {
      for (Category c : multiGame.getCurrentRoundCategories()) {
        if (c.getId() == categoryId) {
          isCategoryInTheGame = true;
          break;
        }
      }
    } else {
      for (Category c : randomGame.getCurrentRoundCategories()) {
        if (c.getId() == categoryId) {
          isCategoryInTheGame = true;
          break;
        }
      }
    }
    return isCategoryInTheGame;
  }

  @Override
  public MultiplayerGame startMultiPlayerGame(String shortCode) {
    MultiplayerGame game = multiplayerGameRepository.findByShortCodeUnfinishedNotStarted(shortCode);
    game.setHasStarted(true);
    multiplayerGameRepository.save(game);
    return game;
  }

}
