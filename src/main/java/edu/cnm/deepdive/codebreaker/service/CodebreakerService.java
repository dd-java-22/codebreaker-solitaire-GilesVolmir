package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import java.util.concurrent.CompletableFuture;

public interface CodebreakerService {

  static CodebreakerService getInstance() {
    return CodebreakerServiceImpl.getInstance();
  }
  CompletableFuture<Game> startGame(Game game);

  CompletableFuture<Game> getGame(Game game);

  CompletableFuture<Void> deleteGame(Game game);

  CompletableFuture<Guess> submitGuess(Guess guess);

  CompletableFuture<Guess> getGuess(Guess guess);

}
