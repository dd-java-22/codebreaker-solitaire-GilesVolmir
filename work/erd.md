```mermaid
game {
  int game_id PK "Primary Key"
  String external_key UK "Unique Key, non-null"
  String pool "Code pool, non-null"
  int length "Length of code, non-null"
  Instant started "Date-time started, non-null"
  boolean solved "Solved flag, non-null"
  Instant last_played "Date-time of last guess"
  int exact_matches "Exact matches in last guess"
  int near_matches "Near matches in last guess"
}
```