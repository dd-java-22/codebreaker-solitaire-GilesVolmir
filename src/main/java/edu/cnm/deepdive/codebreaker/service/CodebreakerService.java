package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import java.util.concurrent.CompletableFuture;

/**
 * Defines the contract for communicating with a codebreaking game service.
 * <p>
 * This interface provides asynchronous methods for creating and managing games,
 * as well as submitting guesses. Implementations typically interact with a
 * remote REST API.
 */
public interface CodebreakerService {

  /**
   * Returns a singleton instance of the default service implementation.
   *
   * @return thread-safe instance of this service
   */
  static CodebreakerService getInstance() {
    return CodebreakerServiceImpl.getInstance();
  }

  /**
   * Initiates a new game on the server with the specified configuration.
   * <p>
   * The {@code game} must have a code length between 1 and 20, and a pool
   * length between 1 and 255. The pool cannot contain whitespace or
   * ISO control characters.
   *
   * @param game object containing the configuration for the new game
   * @return completableFuture representing the pending completion and the resulting game
   * @throws InvalidPayloadException if the game configuration fails validation
   */
  CompletableFuture<Game> startGame(Game game);

  /**
   * Retrieves the current state of an existing game.
   *
   * @param gameId unique identifier of the game to retrieve
   * @return completableFuture representing the pending completion and the requested game
   * @throws ResourceNotFoundException if the specified game identifier is not found
   */
  CompletableFuture<Game> getGame(String gameId);

  /**
   * Deletes an existing game and all associated guesses from the server.
   *
   * @param gameId unique identifier of the game to delete
   * @return completableFuture representing the pending completion of the deletion
   * @throws ResourceNotFoundException if the specified game identifier is not found
   */
  CompletableFuture<Void> deleteGame(String gameId);

  /**
   * Submits a guess for a specific game and receives the evaluation.
   * <p>
   * The guess text must match the game's code length and only contain
   * characters present in the game's pool.
   *
   * @param game game to which the guess is being applied
   * @param guess object containing the guess text
   * @return completableFuture representing the pending completion and the server's response
   * @throws InvalidPayloadException if the guess text length or characters are invalid
   * @throws GameSolvedException if a guess is submitted to a game that is already solved
   * @throws ResourceNotFoundException if the game or guess path is not found
   */
  CompletableFuture<Guess> submitGuess(Game game, Guess guess);

  /**
   * Retrieves a specific guess by its identifier and the game identifier.
   *
   * @param gameId unique identifier of the game containing the guess
   * @param guessId unique identifier of the guess to retrieve
   * @return completableFuture representing the pending completion and the requested guess
   * @throws ResourceNotFoundException if either the game or guess identifier is not found
   */
  CompletableFuture<Guess> getGuess(String gameId, String guessId);

  /**
   * Shuts down the service by terminating the background execution pool.
   * <p>
   * This method evicts all connections from the connection pool and shuts
   * down the executor service responsible for handling asynchronous requests.
   */
  void shutdown();

}