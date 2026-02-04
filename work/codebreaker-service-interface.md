# CodebreakerService Interface

## Overview

The `CodebreakerService` interface defines all API communication methods for the Codebreaker client. This interface is **UI-agnostic** and platform-independent.

---

## Interface Signature

```java
interface CodebreakerService {
  
  CompletableFuture<Game> startNewGame();
  
  CompletableFuture<Game> startNewGame(String pool, int codeLength);
  
  CompletableFuture<Game> getGame(String gameId);
  
  CompletableFuture<Void> deleteGame(String gameId);
  
  CompletableFuture<Guess> submitGuess(String gameId, String guessText);
  
}
```

---

## Method Responsibilities

- `startNewGame()` - Create game with default settings
- `startNewGame(pool, codeLength)` - Create game with custom settings
- `getGame(gameId)` - Retrieve complete game state
- `deleteGame(gameId)` - Delete a game
- `submitGuess(gameId, guessText)` - Submit a guess and receive feedback

---

## Design Principles

1. **UI-Agnostic:** No references to UI frameworks or presentation concerns
2. **Asynchronous:** All methods return CompletableFuture
3. **Domain-Focused:** Returns domain objects (Game, Guess), not DTOs
4. **Platform-Independent:** Works on Console, JavaFX, Android
