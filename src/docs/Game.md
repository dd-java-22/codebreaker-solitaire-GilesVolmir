

# Game


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  [optional] [readonly] |
|**created** | **OffsetDateTime** |  |  [optional] [readonly] |
|**pool** | **String** | Pool of available characters for code and guesses. |  |
|**length** | **Integer** | Length (in characters) of generated code. |  |
|**solved** | **Boolean** |  |  [optional] [readonly] |
|**text** | **String** | Text of secret code. Only included in responses for completed (solved) games. |  [optional] [readonly] |
|**guesses** | [**List&lt;Guess&gt;**](Guess.md) |  |  [optional] [readonly] |



