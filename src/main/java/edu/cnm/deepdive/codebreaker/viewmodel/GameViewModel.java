package edu.cnm.deepdive.codebreaker.viewmodel;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import edu.cnm.deepdive.codebreaker.service.CodebreakerService;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import javafx.application.Platform;

/**
 * Manages the state and business logic of a Codebreaker game session.
 * This class coordinates between the UI and the {@link CodebreakerService},
 * maintaining observable lists of game state, guesses, and errors.
 */
@SuppressWarnings({"UnusedReturnValue", "CallToPrintStackTrace", "unused"})
public class GameViewModel {

  private final CodebreakerService service;
  private final List<Consumer<Game>> gameObservers;
  private final List<Consumer<Guess>> guessObservers;
  private final List<Consumer<Throwable>> errorObservers;
  private final List<Consumer<Boolean>> solvedObservers;

  private Game game;
  private Guess guess;
  private Boolean solved;
  private Throwable error;

  private GameViewModel() {
    service = CodebreakerService.getInstance();
    gameObservers = new LinkedList<>();
    guessObservers = new LinkedList<>();
    errorObservers = new LinkedList<>();
    solvedObservers = new LinkedList<>();
  }

  /**
   * Returns the singleton instance of {@code GameViewModel}.
   *
   * @return singleton instance
   */
  public static GameViewModel getInstance() {
    return Holder.INSTANCE;
  }

  /**
   * Starts a new game with the specified pool and length.
   * <p>
   * Requests a new game from the service and notifies game, solved,
   * and error observers of the results.
   *
   * @param pool string of characters allowed in the secret code
   * @param length number of characters in the secret code
   * @see #registerGameObserver
   * @see #registerErrorObserver
   * @see #registerSolvedObserver
   */
  public void startGame(String pool, int length) {
    Game game = new Game()
        .pool(pool)
        .length(length);
    service
        .startGame(game)
        .thenApply((startedGame) -> setGame(startedGame).getSolved())
        .thenAccept(this::setSolved)
        .exceptionally(this::logError);
  }

  /**
   * Retrieves an existing game by its identifier.
   * <p>
   * Notifies game, solved, and error observers of the retrieved state.
   *
   * @param gameId unique identifier of the game to retrieve
   * @see #registerGameObserver
   * @see #registerErrorObserver
   * @see #registerSolvedObserver
   */
  public void getGame(String gameId) {
    service
        .getGame(gameId)
        .thenApply((game) -> setGame(game).getSolved())
        .thenAccept(this::setSolved)
        .exceptionally(this::logError);
  }

  /**
   * Deletes a game specified by its identifier.
   * <p>
   * Notifies error observers if the deletion fails.
   *
   * @param gameId unique identifier of the game to delete
   * @see #registerErrorObserver
   */
  public void deleteGame(String gameId) {
    service
        .deleteGame(gameId)
        .exceptionally(this::logError);
  }

  /**
   * Deletes the current active game.
   * <p>
   * Notifies game observers of a null state upon success, and error
   * observers if the deletion fails.
   * @see #registerGameObserver
   * @see #registerErrorObserver
   */
  public void deleteGame() {
    service
        .deleteGame(game.getId())
        .thenRun(() -> setGame(null))
        .exceptionally(this::logError);
  }

  /**
   * Submits a guess for the current game.
   * <p>
   * If the guess is the correct solution, the full game state is refreshed; otherwise,
   * the guess is added to the local game history.
   * <p>
   * Notifies guess, game, solved, and error observers of the results.
   *
   * @param text string containing the characters of the guess
   * @see #registerGameObserver
   * @see #registerGuessObserver
   * @see #registerErrorObserver
   * @see #registerSolvedObserver
   */
  public void submitGuess(String text) {
    Guess guess = new Guess()
        .text(text);
    service
        .submitGuess(game, guess)
        .thenApply(this::setGuess)
        .thenAccept((guessResponse) -> {
          if (Boolean.TRUE.equals(guessResponse.getSolution())) {
            getGame(game.getId());
          } else {
            //noinspection DataFlowIssue
            game.getGuesses().add(guessResponse);
            setGame(game);
          }
        })
        .exceptionally(this::logError);
  }

  /**
   * Retrieves a specific guess by its identifier.
   * <p>
   * Notifies guess and error observers of the retrieved state.
   *
   * @param guessId unique identifier of the guess to retrieve
   * @see #registerGuessObserver
   * @see #registerErrorObserver
   */
  public void getGuess(String guessId) {
    service
        .getGuess(game.getId(), guessId)
        .thenAccept(this::setGuess)
        .exceptionally(this::logError);
  }

  /**
   * Shuts down the underlying service.
   */
  public void shutdown() {
    service.shutdown();
  }

  /**
   * Registers an observer to be notified when the {@link Game} state changes.
   * <p>
   * Notifies the provided observer immediately if the game is already set.
   *
   * @param observer consumer that receives the updated game object
   */
  public void registerGameObserver(Consumer<Game> observer) {
    gameObservers.add(observer);
    if (game != null) {
      observer.accept(game);
    }
  }

  /**
   * Registers an observer to be notified when a new {@link Guess} is processed.
   * <p>
   * Notifies the provided observer immediately if a guess is already set.
   *
   * @param observer consumer that receives the processed guess object
   */
  public void registerGuessObserver(Consumer<Guess> observer) {
    guessObservers.add(observer);
    if (guess != null) {
      observer.accept(guess);
    }
  }

  /**
   * Registers an observer to be notified when the game's solved status changes.
   * <p>
   * Notifies the provided observer immediately if the status is already set.
   *
   * @param observer consumer that receives the new solved status
   */
  public void registerSolvedObserver(Consumer<Boolean> observer) {
    solvedObservers.add(observer);
    if (solved != null) {
      observer.accept(solved);
    }
  }

  /**
   * Registers an observer to be notified when an error occurs.
   * <p>
   * Notifies the provided observer immediately if an error is already set.
   *
   * @param observer consumer that receives the throwable error
   */
  public void registerErrorObserver(Consumer<Throwable> observer) {
    errorObservers.add(observer);
    if (error != null) {
      observer.accept(error);
    }
  }

  private Game setGame(Game game) {
    this.game = game;
    Platform.runLater(() -> gameObservers
        .forEach((consumer) -> consumer.accept(game)));
    return game;
  }

  private Guess setGuess(Guess guess) {
    this.guess = guess;
    Platform.runLater(() -> guessObservers
        .forEach((consumer) -> consumer.accept(guess)));
    return guess;
  }

  private Boolean setSolved(Boolean solved) {
    this.solved = solved;
    Platform.runLater(() -> solvedObservers
        .forEach((consumer) -> consumer.accept(solved)));
    return solved;
  }

  private Throwable setError(Throwable error) {
    this.error = error;
    Platform.runLater(() -> errorObservers
        .forEach((consumer) -> consumer.accept(error)));
    return error;
  }

  private Void logError(Throwable error) {
    //noinspection ThrowableNotThrown
    setError(error.getCause() != null ? error.getCause() : error);
    return null;
  }

  private static class Holder {

    static final GameViewModel INSTANCE = new GameViewModel();

  }

}