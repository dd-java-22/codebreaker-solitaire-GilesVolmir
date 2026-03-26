# Create, Read, Update, and Delete operations for Codebreaker GameSummary

## Create Operations
+ After a game is started successfully, when the completed Game instance is returned from the web service.
+ If a tracked game can be input from game ID, when that game instance is first fetched.

## Read Operations
+ When navigating to the "continue game" list, retrieve all summaries for games that are not yet solved, ordered by the last date-time that a guess was submitted—or by the started date-time for the game, if no guesses have yet been submitted.
+ If an in-progress game can be "refreshed", then the game summary should be re-read after it is updated.

## Update Operations
+ After every Guess object is received from the server to update the number of guesses and other summary stats.
+ Whenever a new Game object is received from the server.

## Delete Operations
+ When a game is discovered to not exist on the server anymore.
  + Should inform user.
+ When a user selects the delete game option on a game summary.
+ Hard reset, deleting all games in progress from the device.