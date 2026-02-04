# Codebreaker Client - Technical Specifications and Architecture

## Key Architectural Decisions

### 1. **Dependency Injection**
How do you want to wire dependencies?
- **Manual DI** (constructor injection, factory pattern) - Simple, no framework overhead
- **Dagger 2** - Compile-time DI, works on all platforms (console, JavaFX, Android)
- **Guice** - Runtime DI, simpler than Dagger but doesn't work well on Android
- **Spring** - Full-featured but heavy for desktop/mobile clients

**Decision:** Manual DI with factories (keep it simple initially)

---

### 2. **HTTP Client / REST Client**
Which library for making REST calls?
- **java.net.http.HttpClient** (Java 11+) - Built-in, no dependencies, modern API
- **OkHttp** - Popular, works everywhere, excellent interceptor support
- **Apache HttpClient** - Mature, verbose API
- **Retrofit 2** (built on OkHttp) - Type-safe REST client with annotation-based endpoints

**Decision:** Retrofit 2 + OkHttp

**Rationale:**
- Retrofit 2 provides type-safe, annotation-based API interface (reduces boilerplate by 60-70%)
- Built on OkHttp, so we still get all OkHttp features (interceptors, timeouts, testing)
- Industry standard for REST API clients
- Works on Console, JavaFX, and Android
- Compile-time verification of API endpoints

---

### 3. **JSON Serialization**
How to convert between JSON and Java objects?
- **Gson** - Simple, mature, good for basic use cases
- **Jackson** - Feature-rich, widely used, more complex
- **Moshi** (from Square) - Modern, works well with Kotlin if you go that route

**Decision:** Gson (simple, reliable)

---

### 4. **Async/Reactive Programming**
How to handle asynchronous operations?
- **CompletableFuture** (Java 8+) - Built-in, no dependencies
- **RxJava** - Reactive streams, powerful but steep learning curve
- **Kotlin Coroutines** - If you use Kotlin for Android
- **Project Reactor** - Spring ecosystem reactive library

**Decision:** CompletableFuture (built-in, sufficient for this use case)

---

### 5. **Platform Abstraction Strategy**
How aggressive should we be with platform independence?
- **Option A: Maximum Abstraction** - Pure Java core with zero platform dependencies, everything behind interfaces
- **Option B: Pragmatic Abstraction** - Core business logic platform-agnostic, allow some framework-specific code in infrastructure
- **Option C: Shared Library** - Core as JAR library consumed by separate platform-specific projects

**Decision:** Clean Architecture with strict layer separation

---

### 6. **Domain Models vs DTOs**

#### Option 1: Domain Models = JSON Structure (No DTOs)

**Pros:**
- Simpler - fewer classes to maintain
- Less mapping/conversion code
- Faster initial development

**Cons:**
- **Domain logic tightly coupled to API structure**
- API changes break your entire application
- JSON annotations pollute domain objects
- Can't evolve your internal model independently
- Harder to unit test domain logic (objects tied to serialization concerns)
- Fields like `_links` (HAL) have no business meaning - just API metadata

#### Option 2: Separate DTOs + Domain Models (Recommended)

**Pros:**
- **Domain models represent business concepts, not wire format**
- API changes isolated to DTO layer and mappers
- Clean domain objects - no JSON annotations
- Can validate/transform data during mapping
- Easier unit testing (pure domain objects, no serialization setup)
- Can hide API implementation details (e.g., HAL links, timestamps as strings vs. dates)

**Cons:**
- More classes (DTO + Domain for each entity)
- Mapping/conversion code needed
- Slightly more initial effort

---

## DTO Strategy Details

### Example: Game Entity

**API Response (JSON/DTO):**
```
GameResponse:
    id: String
    pool: String
    length: Integer
    created: String (ISO-8601 timestamp)
    guesses: List of GuessResponse
    code: String (nullable, only when solved)
    _links: Map (HAL links - API metadata)
```

**Domain Model:**
```
Game:
    id: String
    pool: CodePool (rich object with validation)
    codeLength: Integer
    createdAt: Timestamp
    guesses: List of Guess
    solution: Optional String
    
    // No _links - that's infrastructure concern
    
    // Domain methods
    isSolved() -> Boolean
    guessCount() -> Integer
    lastGuess() -> Guess
```

**Benefits:**
1. If API changes `pool` from String to object, only mapper changes
2. Domain can use better types (Timestamp, Optional) while keeping id as String to match API
3. Domain can have business methods without JSON interference
4. Tests don't need to construct valid JSON structures

---

### Mapping Strategy

**Where to put mappers:**

```
Infrastructure Layer:
  - GameResponseDto (matches API JSON)
  - GuessResponseDto
  - GameRequestDto
  - GuessRequestDto
  - GameMapper (DTO ↔ Domain conversion)
  - GuessMapper

Domain Layer:
  - Game (rich domain model)
  - Guess
  - CodePool (value object)
```

**Mapper Concept:**
```
GameMapper:
    
    toDomain(dto: GameResponse) -> Game:
        Game with:
            id = dto.id
            pool = CodePool from dto.pool
            codeLength = dto.length
            createdAt = Timestamp from dto.created
            guesses = map dto.guesses to domain Guess objects
            solution = Optional of dto.code if exists, else empty
    
    toRequest(game: Game) -> GameRequest:
        GameRequest with:
            pool = game.pool as String
            length = game.codeLength
```

---

## Technology Stack Summary

**Rationale:**
- Minimal dependencies
- All choices work on Console, JavaFX, and Android
- Easy to test
- Can migrate to more sophisticated solutions later if needed

| Component | Choice | Why |
|-----------|--------|-----|
| REST Client | Retrofit 2 + OkHttp | Type-safe API, reduces boilerplate, industry standard |
| JSON | Gson | Simple, reliable, integrates with Retrofit |
| Async | CompletableFuture | Built-in, sufficient for this use case |
| DI | Manual DI with factories | Keep it simple initially |
| Architecture | Clean Architecture | Strict layer separation |
| Data Mapping | DTOs + Domain Models | Protect domain from API changes |
