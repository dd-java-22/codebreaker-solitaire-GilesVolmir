# Codebreaker Client - Error Handling Specifications

## Five Error Scenarios and Application Responses

### 1. **404 Not Found - Game Does Not Exist**
**When:** `GET /games/{gameId}` or `POST /games/{gameId}/guesses` with invalid/deleted game ID

**Application Response:**
- Display user-friendly message: "This game could not be found. It may have been deleted or the link is incorrect."
- Offer action: "Start a new game" button
- Log error details for debugging
- Do NOT expose technical details (UUIDs, stack traces) to user

---

### 2. **400 Bad Request - Guess Wrong Length**
**When:** Submitting guess with `text.length != game.length`

**Application Response:**
- Prevent submission with client-side validation BEFORE sending request
- Show inline error: "Your guess must be exactly {length} characters. You entered {actual}."
- Highlight the input field in error state
- If server somehow returns 400 anyway, parse error details and show specific message

---

### 3. **400 Bad Request - Invalid Characters in Guess**
**When:** Guess contains characters not in game's `pool`

**Application Response:**
- Prevent submission with client-side validation
- Show inline error: "Invalid characters detected. Only use characters from: {pool}"
- Highlight the invalid characters in the input
- If server returns 400, extract validation details from response and display clearly

---

### 4. **400 Bad Request - Invalid Pool or Length on Game Creation**
**When:** Creating game with empty pool or invalid length (e.g., length < 1)

**Application Response:**
- Client-side validation: Require pool to have at least 2 characters, length >= 1
- Show field-specific errors: "Pool must contain at least 2 characters" or "Code length must be at least 1"
- Prevent form submission until valid
- If server rejects, parse `details` object from Error response and map to form fields

---

### 5. **Network Error / Timeout - Server Unreachable**
**When:** Network connection lost, server down, request timeout

**Application Response:**
- Display gentle message: "Unable to connect to the game server. Please check your internet connection."
- Offer actions:
  - "Retry" button to repeat the failed request
  - "Continue Offline" (if applicable - view cached game state)
- Cache the failed request data for retry
- Show different icon/color to distinguish from validation errors (e.g., connectivity icon vs. exclamation mark)
- Log full error for diagnostics

---

## Error Handling Principles

**Client-Side Prevention:**
- Validate input before submission when rules are known (length, character pool)
- Provide immediate, specific feedback

**Server-Side Graceful Handling:**
- Parse error response body for details
- Map technical errors to user-friendly messages
- Always offer a path forward (retry, start over, go back)

**Never:**
- Show stack traces or raw JSON to users
- Leave users stuck without options
- Blame the user ("You entered invalid data")
