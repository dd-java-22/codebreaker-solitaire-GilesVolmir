# Single Prompt to Generate Complete Codebreaker Client Design

## The Prompt

Act as a senior software architect designing a Java-based REST API client for the Codebreaker Solitaire game.

**CRITICAL CONSTRAINTS:**
- STRICTLY design-phase only: produce specifications, class signatures (interfaces), and architectural logic
- NO implementation code beyond interface signatures (in Java syntax)
- Use ONLY generic pseudocode for any examples or concepts
- All game and guess IDs are type String (NOT UUID) as specified by the API

**API SOURCE:**
The Codebreaker Solitaire API documentation is at: https://ddc-java.services/codebreaker-solitaire/
Key API facts:
- NO authentication
- Endpoints: POST /codebreaker-solitaire/games, GET /games/{gameId}, POST /games/{gameId}/guesses, DELETE /games/{gameId}
- Game entity has: id (String), pool (String), length (int), created (String timestamp), guesses (array), text (String, nullable - secret code only when solved)
- Guess entity has: id (String), created (String timestamp), text (String), exactMatches (int), nearMatches (int), solution (boolean)
- Server reveals secret code field ONLY when solution=true
- No guess limit - players can guess indefinitely
- API supports any Unicode characters in pool and guesses

**TARGET USERS:**
1. Intellectuals on lunch breaks (want quick mental stimulation, minimal setup)
2. People who fondly remember the codebreaker board game (nostalgic, want classic experience with customization)

**ARCHITECTURE REQUIREMENTS:**
- MVC separation of concerns with platform-agnostic core
- Must support migration path: Console → JavaFX → Android
- Technology Stack:
  * REST Client: Retrofit 2 + OkHttp
  * JSON: Gson
  * Async: CompletableFuture
  * DI: Manual dependency injection with factories
  * Architecture: Clean Architecture with strict layer separation
- Use DTOs to separate API contract from domain models
- Domain models:
  * Game (mutable, can add guesses and update from server)
  * Guess (immutable)
  * GuessResult (immutable value object)
  * CodePool (immutable value object for character validation)
  * GameConfiguration (immutable value object for game creation)
- All IDs (game, guess) are String type per API spec

**UI APPROACH FOR UNICODE FLEXIBILITY:**
Combine these approaches:
1. Default: Pre-populate with "ROYGBIV" and length 4 (classic board game)
2. Label: "Character Pool" (not "colors")
3. Help icon: "Use any characters - letters, numbers, emojis (🎨), symbols (αβ), etc."
4. Examples button: Shows preset pools for quick selection
5. No artificial restrictions: Let users type freely, validate on submission

**DELIVERABLES (6 files):**

1. **user-stories.md** - User stories with these exact personas and structure:
   - Game Management section (4 stories alternating personas)
   - Gameplay section (3 stories alternating personas)  
   - User Experience section (2 stories alternating personas)
   - Each story on separate line with blank line after
   
2. **http-interaction-flow.md** - Generic ASCII diagram showing:
   - POST /codebreaker-solitaire/games → 201 Game response
   - POST /games/{gameId}/guesses → 201 Guess response
   - GET /games/{gameId} → 200 Game with all guesses
   - Key points section emphasizing:
     * Each guess POST returns only new Guess (not full Game)
     * Client tracks state locally OR fetches full game
     * solution: true signals completion
     * Server reveals code field ONLY when solution: true

3. **error-handling.md** - Five specific scenarios:
   - 404 Not Found - Game Does Not Exist
   - 400 Bad Request - Guess Wrong Length
   - 400 Bad Request - Invalid Characters in Guess
   - 400 Bad Request - Invalid Pool or Length on Game Creation
   - Network Error / Timeout - Server Unreachable
   Each with: When it occurs, Application Response (user message, actions, technical handling)
   Plus Error Handling Principles section (Client-Side Prevention, Server-Side Graceful Handling, Never do these things)

4. **technical-architecture.md** - Comprehensive document with:
   - Key Architectural Decisions (6 sections with options and decisions)
   - Domain Models vs DTOs discussion (Option 1 vs Option 2 with pros/cons)
   - DTO Strategy Details with:
     * API Response (GameResponse in generic pseudocode)
     * Domain Model (Game in generic pseudocode)
     * Benefits list (4 items)
     * Mapping Strategy showing Infrastructure vs Domain layers
     * Mapper Concept (generic pseudocode)
   - Technology Stack Summary table

5. **codebreaker-service-interface.md** - Minimal interface document with:
   - Overview paragraph
   - Interface Signature in Java:
     ```java
     interface CodebreakerService {
       CompletableFuture<Game> startNewGame();
       CompletableFuture<Game> startNewGame(String pool, int codeLength);
       CompletableFuture<Game> getGame(String gameId);
       CompletableFuture<Void> deleteGame(String gameId);
       CompletableFuture<Guess> submitGuess(String gameId, String guessText);
     }
     ```
   - Method Responsibilities (bullet list)
   - Design Principles (4 numbered items)

6. **class-diagram.mermaid** - Complete Mermaid class diagram showing:
   - MODEL Layer: Game, Guess, GuessResult, CodePool, GameConfiguration, GameRepository interface
   - CONTROLLER Layer: GameController, ValidationResult
   - VIEW Layer: GameView interface, ConsoleGameView, JavaFXGameView, AndroidGameView
   - INFRASTRUCTURE Layer: RetrofitGameRepository, CodebreakerApiService interface, GameMapper, GuessMapper, all DTOs (GameRequestDto, GameResponseDto, GuessRequestDto, GuessResponseDto)
   - APPLICATION: ConsoleApplication
   - All relationships: composition, usage, implementation
   - Use simplified syntax (no generics/tildes, just "List" not "List<T>")
   - Game fields: id (String), pool (CodePool), codeLength (int), createdAt (Timestamp), guesses (List), text (String)
   - All IDs shown as String type

**FORMATTING RULES:**
- Use generic pseudocode for all examples (not Java implementation)
- Class/method signatures: only for interfaces (Java syntax allowed)
- User stories: each numbered item on separate line with blank line after
- Error handling: use ### for scenario headers, ** for When/Application Response labels
- All documents: use proper markdown formatting with headers, code blocks, lists

Generate all 6 artifacts with exact structure and content as specified above.
