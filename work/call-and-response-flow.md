# Codebreaker API - HTTP Interaction Flow

## Generic HTTP Interaction Flow

```
CLIENT                                          SERVER
  |                                               |
  | POST /codebreaker-solitaire/games             |
  | Body: { pool?, length? }                      |
  |---------------------------------------------->|
  |                                               |
  | 201 Created                                   |
  | Body: Game { id, pool, length, created,       |
  |              guesses: [], _links }            |
  |<----------------------------------------------|
  |                                               |
  | POST /games/{gameId}/guesses                  |
  | Body: { text }                                |
  |---------------------------------------------->|
  |                                               |
  | 201 Created                                   |
  | Body: Guess { id, created, text,              |
  |               exactMatches, nearMatches,      |
  |               solution }                      |
  |<----------------------------------------------|
  |                                               |
  | [Repeat guess submissions until solution=true]|
  |                                               |
  | GET /games/{gameId}                           |
  | [Optional: view complete game state]          |
  |---------------------------------------------->|
  |                                               |
  | 200 OK                                        |
  | Body: Game { id, pool, length, created,       |
  |              guesses: [...], _links,          |
  |              code? }                          |
  |<----------------------------------------------|
  |                                               |
```

## Key Points

- Each guess POST returns only the new `Guess` object (not the full `Game`)
- Client must track game state locally OR fetch full game via GET to see all guesses
- The `solution: true` flag signals game completion
- Server reveals the secret `code` field in the `Game` object ONLY when `solution: true`