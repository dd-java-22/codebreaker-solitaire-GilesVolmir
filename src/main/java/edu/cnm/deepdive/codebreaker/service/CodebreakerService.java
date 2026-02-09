package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import java.util.concurrent.CompletableFuture;

enum CodebreakerService implements AbstractCodebreakerService{

  INSTANCE;

  private final CodebreakerApi api;

  CodebreakerService() {
    // TODO: 2/9/2026 DO initialization of Gson, Retrofit, and CodebreakerApi.
  }

  @Override
  public CompletableFuture<Game> startGame(Game game) {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  @Override
  public CompletableFuture<Game> getGame(Game game) {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  @Override
  public CompletableFuture<Void> deleteGame(Game game) {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  @Override
  public CompletableFuture<Guess> submitGuess(Guess guess) {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  @Override
  public CompletableFuture<Guess> getGuess(Guess guess) {
    throw new UnsupportedOperationException("Not yet implemented.");
  }
}
