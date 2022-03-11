API Docs for react native trivia game

 MAIN ENDPOINT /trivia/api
 
| GET | showWelcomePlayer()
- ENDPOINT /players
- no deviceId -> Bad request 
- player null -> Not found
- accepts Player's unique deviceId in header as a parameter
- Player found
  - returns player's object
  - sets player.isActive to true
- Player not found
  - returns not found


| POST | storePlayer()
 - ENDPOINT /players
 - no deviceId -> bad request
 - no username -> bad request
 - deviceId already in db -> forbidden
 - accepts RequestParam username
 - deviceId in header
 -> https://aboutreact.com/react-native-get-unique-id-of-device/
 - sets player.isActive to true
 - returns player's object


 | POST | storeMultiPlayerGame()
 - ENDPOINT /games/multiplayer
 - players deviceId in header
 - no deviceId -> Bad request
 - player null -> Not found on db level searches for game with the player based on deviceId which is unfinished and hasn't started, if is, returns short unique current game code
 - generates new Game Object with unique game hashcode
 - assigns list of six categories for the game
 - isFinished = false
 - hasStarted = false
 - sets a player based on deviceId as a hostPlayer
 - returns game object with generated shortcode to share with other players
 - FE stores the response in order to connect the game when the game starts


| POST | storeRandomPlayerGame()
- ENDPOINT /games/random-player
- players deviceId in header
- no deviceId -> Bad request 
- player null -> Not found
- player trying to join their own game -> Forbidden
- on db level searches for game which is not finished and hasn't started chooses the first from the list based on ordering by short code ASC
  - if found
  - if host player active -> sets hasStarted -> true
  - -> sets player based on deviceId as a joining player, saves the game and returns the saved game
  - -> in endpoint condition if game.hasStarted -> return showCategories()
  - -> Starts game
  - if not 
  - -> sets isFinished = true, calls another waiting game or generates a new one recursively
  - if not found
    - creates a new RandomPlayerGame object 
    - isFinished = false
    - hasStarted = false
    - sets player as a host player
    - generates six random categories
    - saves and returns
    - TODO when game is about to be connected check if the host player is active, if not send new request
                          -> when game connected (both host player and joining player not null )send request to show categories
 
| POST | joinGame()
- ENDPOINT /games/{gameCode}
- no gameCode -> Bad request
- no deviceId -> Bad request 
- player null -> Not found
- no unfinished game found based on game code -> Not found
- joining players.size > 5 -> Forbidden
- player trying to join their own game - Forbidden
- players deviceId in header 
- game code as a path variable 
- adds player to the list of joining players 
- saves and returns the game object with added player
- FE should notify the host player of added player based on requests to refresh - TBD
  

| PUT | startMultiPlayerGame()

- ENDPOINT games/{gameCode}
- no gameCode -> Bad request 
- no deviceId -> Bad request 
- game null -> not found 
- player null -> not found 
- finds the game based on game code which is not started, not finished 
- changes to started 
- saves 
- returns the game object


| GET | showCategories() 
- ENDPOINT games/{gameId}/categories
- no gameCode -> Bad request  no deviceId -> Bad request 
- player not in the game -> Forbidden 
- player null -> Not found 
- game null -> Not found 

- returns the whole game object -> either random or multi game 
- -> this way all the info will be accessible like player name, player current score etc each time the view is reloaded 
- when looking for game searches for one with the gameCode which is unfinished and started 
- each player chooses their own category based on spin of the wheel on FE
- FE fortune wheel with 6 categories
 

| GET | getQuestion() 
- ENDPOINT games/{gameId}/categories/{id}/questions 
- no gameCode -> Bad request 
- no deviceId -> Bad request 
- game null -> not found 
- player null -> not found 
- random as query param - number of questions to be generated -> no random -> bad request 
- null category in the game -> not found 
- player not in game -> forbidden 
- chooses random question from the category 
- directly to db sends request to find the question id in answered questions of the player -> if match, recursively calls for another -> TODO if player answered all questions in category, choose different category 
- shuffles the answers 
- sets question as current question for the player based on player.deviceId 
- each player has their own question 
- returns gameDTO object 
- -> same fields as Game object tho only one player, the player is assigned to the DTO object based on deviceId passed in header
                      this way the FE will be able to easily render the question for the player on the specific device without conditioning for deviceId = deviceId 

 
| GET | checkAnswer() 
- ENDPOINT games/{gameId}//categories/{categoryId}/questions/{questionId} 
- answerId as requestParam 
- returns GameDTO 
- Increment points (current game and total) if correct 
- increments number of answered questions in the game 
- when all players had answered, increments the roundNumber of the game - FE based on incremented roundNumber showsResults 
- sets didAnswerQuestionCorrectly according to answer 
- nulls question for each player -> when then showing categories, no player has question assigned 
- FE async await -> waits couple seconds to show the result of the question -> calls GET results
       
| GET | showResults()
 - ENDPOINT games/{gameId}/players 
 - FE tries send request -> when the answered questions number isnt same returns conflict and tries again till the number is same
 - resets didAnswerQuestionCorrectly to null 
 - till the condition is true the view shows "waiting for all players to answer"
 - FE renders results, button continue game -> GET categories 
 - when 10th round sets game to finished 
 - returns the whole game object when, when returned FE checks the round number, if 10 -> end game view