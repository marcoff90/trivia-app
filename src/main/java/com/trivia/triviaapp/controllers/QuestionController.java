package com.trivia.triviaapp.controllers;

import com.trivia.triviaapp.factories.ErrorFactory;
import com.trivia.triviaapp.services.categoryservices.CategoryService;
import com.trivia.triviaapp.services.questionservices.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trivia/api")
public class QuestionController {

  private QuestionService questionService;
  private ErrorFactory errorFactory;
  private CategoryService categoryService;

  @Autowired
  public QuestionController(QuestionService questionService, ErrorFactory errorFactory,
      CategoryService categoryService) {
    this.questionService = questionService;
    this.errorFactory = errorFactory;
    this.categoryService = categoryService;
  }

//  @GetMapping("/categories/{id}/questions")
//  public ResponseEntity<Object> showCategoryQuestions(@PathVariable(required = false) Integer id,
//      @RequestParam(required = false) Integer random) {
//    if (random == null) {
//      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Number of questions is needed!"));
//    } else if (categoryService.isCategoryInDatabase(id)) {
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.getNewErrorMessage("Category doesn't exist!"));
//    } else {
//      return ResponseEntity.ok().body(questionService.getRandomQuestionsFromCategory(id, random));
//    }
//  }

  @GetMapping("/categories/{categoryId}/questions/{questionId}")
  public ResponseEntity<Object> checkAnswer(@PathVariable(required = false) Integer categoryId, @PathVariable(required = false) Integer questionId,
      @RequestParam(required = false) Integer tip) {
    if (tip == null) {
      return ResponseEntity.badRequest().body(errorFactory.getNewErrorMessage("Tip needs to be provided!"));
    } else {
      return ResponseEntity.ok().body(questionService.checkAnswer(questionId, tip));
    }
  }
}

/**
 *  TODO
 *    -> make methods which check the statuses of the users in waiting game, when inactive, sets the game to finished
 *          -> methods called regularly in endpoints or based on timer runs all the time in interval
 *
 *
 *  DEVELOPMENT UNDER DEV_DATABASE -> REAL_DB WHEN FINISHED
 *
 * MODELS
 *    | GAME |
 *        hashcode
 *        Player hostPlayer
 *        List<Player> - checked on api to max size 6  -> game is full
 *        - for history purposes
 *        ! different table for random player game?
 *        Player randomGamePlayerHost
 *        Player randomGamePlayerJoining
 *
 *
 *    | CURRENT-GAME |
 *       same fields as GAME
 *       when game ends, move from current game to GAME
 *       has List<Category> - when the game is instantiated, the first random 6 categories are assigned, with each checkAnswer
 *            the new Categories are assigned - this way all users sees the same categories
 *       Question currentRoundQuestion - similar to categories
 *       int roundNumber
 *
 *    | PLAYER |
 *        hashcode of the game he's in
 *        score
 *        level
 *        current_game_score
 *        List<Question> answeredQuestions - when question was shown to the player, it's ID is saved into the list
 *                                         - Question service checks if the player hasn't seen the question
 *
 *    | TIP-CHECKER |
 *        TODO change Message to tip checker -> returns boolean isTipCorrect
 *
 *
 * API
 *  ! MAIN ENDPOINT /trivia/api
 *
 * DONE    | GET | showWelcomePlayer()
 *             ! ENDPOINT /players
 *             ? no hashcode -> Bad request
 *             ? player null -> Not found
 *             * accepts Player's unique hashcode in header as a parameter
 *             * Player found
 *                 * returns player's name
 *                 * sets player.isActive to true
 *                 * returns randomly chosen welcome message
 *             * Player not found
 *                 * returns not found
 * DONE    | POST | storePlayer()
 *             ! ENDPOINT /players
 *             ? no hashcode -> bad request
 *             ? no username -> bad request
 *             ? hashcode already in db -> forbidden
 *             * accepts RequestParam username
 *             * deviceId in header
 *                    -> https://aboutreact.com/react-native-get-unique-id-of-device/
 *             * returns player's name
 *             * sets player.isActive to true
 *             * returns randomly chosen welcome message
 * DONE    | POST | storeMultiPlayerGame()
 *             ! ENDPOINT /games/multiplayer
 *             * players hashcode in header
 *             ? no hashcode -> Bad request
 *             ? player null -> Not found
 *             * on db level searches for game with the player based on hashcode which is unfinished and hasn't started
 *                    * if is, returns short unique current game code
 *             * generates new Game Object with unique game hashcode
 *                    * assigns first list of categories
 *                    * isFinished = false
 *                    * hasStarted = false
 *             * sets a player based on hashcode as a hostPlayer
 *             * returns unique short game code (to share with friends to join)
 *             * FE stores the response in order to connect the game when the game starts
 * DONE    | POST | storeRandomPlayerGame()
 *             ! ENDPOINT /games/random-player
 *             * players hashcode in header
 *             ? no hashcode -> Bad request
 *             ? player null -> Not found
 *             ? player trying to join their own game -> Forbidden
 *             * on db level searches for game which is not finished and hasn't started chooses the first from the list based on ordering by short code ASC
 *                   * if found
 *                   * if host player active -> sets hasStarted -> true
 *                      -> sets player based on hashcode as a joining player, saves the game and returns the saved game
 *                      -> in endpoint condition if game.hasStarted -> return showCategories()
 *                          -> Starts game
 *                   * if not -> sets isFinished = true, calls another waiting game or generates a new one recursively
 *                * if not found
 *                   * creates a new RandomPlayerGame object
 *                   * isFinished = false
 *                   * hasStarted = false
 *                   * sets player as a host player
 *                   * generates six random categories
 *                   * saves and returns
 *                      TODO when game is about to be connected check if the host player is active, if not send new request
 *                      -> when game connected (both host player and joining player not null )send request to show categories
 *
 * DONE    | POST | joinGame()
 *             ! ENDPOINT /games/{gameCode}
 *             ? no gameCode -> Bad request
 *             ? no hashcode -> Bad request
 *             ? player null -> Not found
 *             ? no unfinished game found based on game code -> Not found
 *             ? joining players.size > 5 -> Forbidden
 *             ? player trying to join their own game - Forbidden
 *             * players hashcode in header
 *             * game code as a path variable
 *                   * adds player to the list of joining players
 *                   * saves and returns the game
 *
 *    ! EDIT ALREADY DONE ENDPOINTS TO THIS LOGIC
 * DONE    | GET | showCategories()
 *             ? no gameCode -> Bad request
 *             ? no hashcode -> Bad request
 *             ? player not in the game -> Forbidden
 *             ? player null -> Not found
 *             ? game null -> Not found
 *             ? in endpoint either game id or unique short game code
 *             ! returns the whole game object -> either random or multi game
 *                    -> this way all the info will be accessible like player name, player current score etc each time the view is reloaded
 *             ! ENDPOINT games/{gameId}/categories
 *             * when looking for game searches for one with the gameCode which is unfinished and started
 *             * each player chooses their own category based on spin of the wheel
 *             * categories are saved for each round under the current game id in db
 *             * returns either randomGame categories or multiGame categories based on which type is found in db based on gameCode
 *             * FE fortune wheel with 6 categories
 *
 *     | PUT | startMultiPlayerGame()
 *             ! ENDPOINT games/{gameCode}
 *             ? no gameCode -> Bad request
 *             ? no hashcode -> Bad request
 *             ? game null -> not found
 *             ? player null -> not found
 *             * finds the game based on game code which is not started, not finished
 *             * changes to started
 *             * saves
 *             * returns the game object
 *
 *     | GET | getQuestion()
 *             ! ENDPOINT games/{gameId}//categories/{id}/questions
 *             ? no gameCode -> Bad request
 *             ? no hashcode -> Bad request
 *             ? game null -> not found
 *             ? player null -> not found
 *             ? no random -> bad request
 *             ? null category in the game -> not found
 *             ? player not in game -> forbidden
 *             * chooses random question from the category
 *             * directly to db sends request to find the question id in answered questions of the player -> if match, recursively calls for another
 *             * shuffles the answers
 *             * sets question as current question for the player based on player.deviceId
 *             * each player has their own question -> the possibility of breaking the code when searching for the question which either of players had already
 *                  answered is too great
 *             * returns game object
 *        TODO
 *             * assigns new List<Category> to the game so it can be loaded after the checkAnswer
 *             * if roundNumber / 5 == 0
 *                    * increments lightingRoundQuestions
 *
 *     | GET | checkAnswer()
 *             ! ENDPOINT games/{gameId}//categories/{categoryId}/questions/{questionId}
 *             * returns AnswerChecker
 *             * increments points if correct
 *             * increments player level according to the algorithm to be made
 *             * assigns new question for next round to the game object
 *             * FE async await -> waits couple seconds to show the result of the question -> calls GET results
 *             * if roundNumber / 5 == 0 & lightingRoundQuestions < 5
 *                    * calls getQuestion
 *     | GET | showResults()
 *             ! ENDPOINT games/{gameId}/players
 *             * if roundNumber / 5 == 0 & lightningRoundQuestions == 5
 *                  * sets lightningRoundQuestions to 0
 *             * returns List<Player> of the game object including host
 *             * increments roundNumber
 *             * FE renders results, button continue game -> GET categories
 *
 *     | POST | logOut()
 *             ! DURING GAME IF EVER ANY PLAYER CLOSES THE APP, THE PLAYER isACTIVE STATUS TO FALSE, DELETES THE PLAYER FROM LIST OF PLAYERS IN GAME OBJECT
 *                  ! -> IF HOST PLAYER, THE CURRENT GAME IS FINISHED -> INFO TO ALL PLAYERS
 *
 *     | POST | endGame()
 *             * after fifth round
 *             * sets status of game to finished
 *
 * UI
 *    | HOME SCREEN |
 *        - when app is started -> GET to API
 *             - checks if the player's unique code (based on react native library for users) is in database or not
 *             - returns either player's name or that no player found
 *        - player not found
 *             - Welcome message
 *             - Basic info about game
 *             - sign up form
 *                  - just username
 *                  - BUTTON POST storePlayer()
 *                  - after save reload with player found
 *        - player found
 *             - Welcome back player's name message (Maybe different messages, randomly chosen by API and send in response)
 *             - Top of the view
 *                   - player info
 *                        - username
 *                        - points
 *                        - level
 *                        - other features / special objects later
 *            - Sides
 *                    - TODO for future dev
 *            - Bottom in middle
 *                    - Button Start Game
 *
 *    | START GAME BUTTON |
 *        - button is a function/class with props for text, style and maybe function - based on passed function name API request is performed
 *        - loads new view, the rest is hidden
 *        - three buttons
 *             - Play with friends - POST to API
 *             - Play with random player - GET to API
 *             - Join game - new view JOIN GAME
 *
 *    | PLAY WITH FRIENDS |
 *        - message "waiting for friends to join"
 *        - when someone joins
 *            - message " name player joined the game, start the game? -> button to start the game -> GET to API
 *
 *    | PLAY WITH RANDOM PLAYER |
 *        - message "looking for other player"
 *        - connects the game on API
 *
 *    | JOIN GAME |
 *        - form enter game code
 *        -> GET to API findGame
 *        - if found message waiting for host to start the game
 *        - not found -> message not found button try again, reloads the view
 *
 *    | CATEGORY |
 *        - wheel of fortune with six categories
 *        - obtained by GET to API
 *
 *    | QUESTION |
 *        - when host player of multiplayer starts the game -> GET to API
 *        - when game is connected on random player game
 *        - Question has it's own component, the question itself is passed as a props
 *        - Answers are buttons - text through props, onPress function through props, style through props
 *
 */
