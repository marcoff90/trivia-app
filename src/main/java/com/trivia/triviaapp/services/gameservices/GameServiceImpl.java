package com.trivia.triviaapp.services.gameservices;

import com.trivia.triviaapp.factories.GameDTOFactory;
import com.trivia.triviaapp.models.Category;
import com.trivia.triviaapp.models.Player;
import com.trivia.triviaapp.models.Question;
import com.trivia.triviaapp.models.game.Game;
import com.trivia.triviaapp.models.game.GameDTO;
import com.trivia.triviaapp.models.game.MultiplayerGame;
import com.trivia.triviaapp.models.game.RandomPlayerGame;
import com.trivia.triviaapp.repositories.MultiplayerGameRepository;
import com.trivia.triviaapp.repositories.PlayerRepository;
import com.trivia.triviaapp.repositories.RandomPlayerGameRepository;
import com.trivia.triviaapp.services.categoryservices.CategoryService;
import com.trivia.triviaapp.services.playerservices.PlayerService;
import com.trivia.triviaapp.services.questionservices.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

  private MultiplayerGameRepository multiplayerGameRepository;
  private PlayerService playerService;
  private CategoryService categoryService;
  private RandomPlayerGameRepository randomPlayerGameRepository;
  private QuestionService questionService;
  private PlayerRepository playerRepository;
  private GameDTOFactory gameDTOFactory;

  @Autowired
  public GameServiceImpl(MultiplayerGameRepository multiplayerGameRepository,
      PlayerService playerService, CategoryService categoryService,
      RandomPlayerGameRepository randomPlayerGameRepository, QuestionService questionService,
      PlayerRepository playerRepository, GameDTOFactory gameDTOFactory) {
    this.multiplayerGameRepository = multiplayerGameRepository;
    this.playerService = playerService;
    this.categoryService = categoryService;
    this.randomPlayerGameRepository = randomPlayerGameRepository;
    this.questionService = questionService;
    this.playerRepository = playerRepository;
    this.gameDTOFactory = gameDTOFactory;
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
  public GameDTO getQuestionForTheRound(String shortCode, Integer categoryId, String deviceId, Integer random) {
    RandomPlayerGame randomGame = randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    MultiplayerGame multiGame = multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    Question q = questionService.getRandomQuestionsFromCategory(categoryId, random, deviceId).get(0);
    Player p = playerService.findByDeviceId(deviceId);
    p.setCurrentQuestion(q);
    playerRepository.save(p);

    if (randomGame == null) {
      return gameDTOFactory.getNewGameDTOMultiplayer(multiGame, p);
    } else {
      return gameDTOFactory.getNewGameDTORandomPlayer(randomGame, p);
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

  @Override
  public GameDTO checkAnswer(String shortCode, String deviceId, Integer categoryId, Integer questionId, Integer answerId) {
    RandomPlayerGame randomGame = randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    MultiplayerGame multiGame = multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    Player player = playerService.findByDeviceId(deviceId);
    Question q = player.getCurrentQuestion();

    if (q.getCorrectAnswerId() == answerId) {
      player.setCurrentGameScore(player.getCurrentGameScore() + 1);
      player.setTotalScore(player.getTotalScore() + 1);
      player.setCurrentQuestion(null);
      player.setDidAnswerQuestionCorrectly(true);
    } else {
      player.setCurrentQuestion(null);
      player.setDidAnswerQuestionCorrectly(false);
    }
    player.setNumberOfAnsweredQuestionInOneGame(player.getNumberOfAnsweredQuestionInOneGame() + 1);
    playerRepository.save(player);

    if (randomGame == null) {
      if (didAllPlayersAnsweredInMultiGame(shortCode)) {
        multiGame.setRoundNumber(multiGame.getRoundNumber() + 1);
        multiplayerGameRepository.save(multiGame);
      }
      return gameDTOFactory.getNewGameDTOMultiplayer(multiGame, player);
    } else {
      if (randomGame.getHostPlayer().getNumberOfAnsweredQuestionInOneGame() == randomGame.getJoiningPlayer()
          .getNumberOfAnsweredQuestionInOneGame()) {
        randomGame.setRoundNumber(randomGame.getRoundNumber() + 1);
        randomPlayerGameRepository.save(randomGame);
      }
      return gameDTOFactory.getNewGameDTORandomPlayer(randomGame, player);
    }
  }

  @Override
  public Game showRoundResults(String shortCode, String deviceId) {
    RandomPlayerGame randomGame = randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    MultiplayerGame multiGame = multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);

    if (randomGame == null) {
      multiGame.getJoiningPlayers().forEach(p -> p.setDidAnswerQuestionCorrectly(null));
      playerRepository.saveAll(multiGame.getJoiningPlayers());
      multiGame.getHostPlayer().setDidAnswerQuestionCorrectly(null);
      playerRepository.save(multiGame.getHostPlayer());
      // * sets state of answer to null to all players

      if (multiGame.getRoundNumber() == 10) {
        multiGame.setFinished(true);
        multiplayerGameRepository.save(multiGame);
        // * sets status of the game to finished when 10th round
      }
      return multiGame;

    } else {
      randomGame.getJoiningPlayer().setDidAnswerQuestionCorrectly(null);
      playerRepository.save(randomGame.getJoiningPlayer());
      randomGame.getHostPlayer().setDidAnswerQuestionCorrectly(null);
      playerRepository.save(randomGame.getHostPlayer());

      if (randomGame.getRoundNumber() == 10) {
        randomGame.setFinished(true);
        randomPlayerGameRepository.save(randomGame);
      }
      return randomGame;
    }
  }

  @Override
  public boolean didAllPlayersAnsweredInMultiGame(String shortCode) {
    MultiplayerGame game = multiplayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    List<Player> allPlayers = game.getJoiningPlayers();
    allPlayers.add(game.getHostPlayer());
    return allPlayers.stream().map(Player::getNumberOfAnsweredQuestionInOneGame).distinct().count() <= 1;
  }

  @Override
  public boolean didAllPlayersAnsweredInRandomGame(String shortCode) {
    RandomPlayerGame randomGame = randomPlayerGameRepository.findByShortCodeNotFinishedStarted(shortCode);
    return randomGame.getHostPlayer().getNumberOfAnsweredQuestionInOneGame() == randomGame.getJoiningPlayer()
        .getNumberOfAnsweredQuestionInOneGame();
  }
}
