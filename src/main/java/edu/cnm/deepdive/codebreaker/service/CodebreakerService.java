package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import java.util.concurrent.CompletableFuture;

/**
 * Defines the contract for communicating with a codebreaking game service.
 * This interface includes methods for managing games and submitting guesses.
 */
public interface CodebreakerService {

  /**
   * Returns an instance of the default implementation of {@code CodebreakerService}.
   *
   * @return service instance
   */
  static CodebreakerService getInstance() {
    return CodebreakerServiceImpl.getInstance();
  }

  /**
   * Initiates a new game on the server.
   *
   * @param game object containing the configuration for the new game
   * @return completableFuture representing the pending completion and the resulting game
   */
  CompletableFuture<Game> startGame(Game game);

  /**
   * Retrieves the state of an existing game.
   *
   * @param gameId unique identifier of the game to retrieve
   * @return completableFuture representing the pending completion and the requested game
   */
  CompletableFuture<Game> getGame(String gameId);

  /**
   * Deletes a game from the server.
   *
   * @param gameId unique identifier of the game to delete
   * @return completableFuture representing the pending completion of the deletion
   */
  CompletableFuture<Void> deleteGame(String gameId);

  /**
   * Submits a guess for a specific game.
   *
   * @param game game to which the guess is being applied
   * @param guess object containing the guess text
   * @return completableFuture representing the pending completion and the server's response to the guess
   */
  CompletableFuture<Guess> submitGuess(Game game, Guess guess);

  /**
   * Retrieves a specific guess by its identifier.
   *
   * @param gameId unique identifier of the game containing the guess
   * @param guessId unique identifier of the guess to retrieve
   * @return completableFuture representing the pending completion and the requested guess
   */
  CompletableFuture<Guess> getGuess(String gameId, String guessId);

  /**
   * Performs any necessary cleanup and terminates the service.
   */
  void shutdown();

}