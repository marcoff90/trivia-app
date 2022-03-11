TRIVIA GAME API

- application is build to be connected to React Native app
- the player is registered through username and device id acquired from the device through react native library
- when the player is logged in he gets to choose from three options
  - start multiplayer game
  - start random player game
  - join multiplayer game


Game in general
- when game object is generated, list of 6 categories is assigned
- each game object has booleans isFinished, hasStarted which tracks the state of the game
- each game has generated unique hashcode and unique shortcode - the shortcode is used as path variable
- each round each player chooses random question from one of the categories
- after each guess the application waits till all players have guessed
- then it shows the results
- the game takes 10 rounds


Multiplayer game
- up to 6 players
- the host player is set as host player
- the joining players are added to the list (app checks the size of the list to not exceed the limit of 5)
- multiplayer game is started through endpoint when one of the players starts the game

RandomPlayerGame
- host player/joining player
- game starts automatically when there was a game waiting in database
- if theres no waiting game, the player becomes host player and waits for joining player

Player
- each round player gets assigned new current round question
- after answering the question is added to the list of answered questions under the player -> the player wont get the same question twice


[Endpoint docs](api-docs.md)
