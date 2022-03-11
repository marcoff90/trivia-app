
/**
 * TODO
 *    -> make methods which check the statuses of the users in waiting game, when inactive, sets the game to finished
 *          -> methods called regularly in endpoints or based on timer runs all the time in interval
 *
 * ? IDEAS
 *
 *
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
   | POST | storeRandomPlayerGame()
 *                      TODO when game is about to be connected check if the host player is active, if not send new request
 *                      -> when game connected (both host player and joining player not null )send request to show categories
 *

 | GET | getQuestion()
 *
 *        TODO
 *             * assigns new List<Category> to the game so it can be loaded after the checkAnswer
 *             * if roundNumber / 5 == 0
 *                    * increments lightingRoundQuestions
 *
  | GET | checkAnswer()
 *
 *        TODO
 *             * if roundNumber / 5 == 0 & lightingRoundQuestions < 5
 *                    * calls getQuestion
 *             *             * increments player level according to the algorithm to be made
 *
 * DONE    | GET | showResults()

 *      TODO
 *             * endpoint for prolonging the game after the view end of game offer 5 another rounds, finds game, sets not finished and continues
 *
 *
 * TODO    | POST | logOut()
 *             ! DURING GAME IF EVER ANY PLAYER CLOSES THE APP or puts to the background,
 *             THE PLAYER isACTIVE STATUS TO FALSE, DELETES THE PLAYER FROM LIST OF PLAYERS IN GAME OBJECT
 *                  ! -> IF HOST PLAYER, THE CURRENT GAME IS FINISHED -> INFO TO ALL PLAYERS
 *                  https://stackoverflow.com/questions/38962034/how-to-detect-when-a-react-native-app-is-closed-not-suspended
 *
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
