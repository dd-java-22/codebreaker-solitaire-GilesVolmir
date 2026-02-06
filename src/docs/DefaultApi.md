# DefaultApi

All URIs are relative to *https://ddc-java.services/codebreaker-solitaire*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteGame**](DefaultApi.md#deleteGame) | **DELETE** games/{gameId} | Delete a game resource |
| [**getGame**](DefaultApi.md#getGame) | **GET** games/{gameId} | Retrieve a game resource |
| [**getGuess**](DefaultApi.md#getGuess) | **GET** games/{gameId}/guesses/{guessId} | Retrieve a specific guess resource |
| [**startGame**](DefaultApi.md#startGame) | **POST** games | Start a new game |
| [**submitGuess**](DefaultApi.md#submitGuess) | **POST** games/{gameId}/guesses | Submit a new guess for a game |



## deleteGame

> deleteGame(gameId)

Delete a game resource

### Example

```java
// Import classes:
import edu.cnm.deepdive.codebreaker.ApiClient;
import edu.cnm.deepdive.codebreaker.ApiException;
import edu.cnm.deepdive.codebreaker.Configuration;
import edu.cnm.deepdive.codebreaker.models.*;
import edu.cnm.deepdive.codebreaker.service.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://ddc-java.services/codebreaker-solitaire");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String gameId = "gameId_example"; // String | 
        try {
            apiInstance.deleteGame(gameId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#deleteGame");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **gameId** | **String**|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Specified game deleted. |  -  |
| **404** | Game not found. |  -  |


## getGame

> Game getGame(gameId)

Retrieve a game resource

### Example

```java
// Import classes:
import edu.cnm.deepdive.codebreaker.ApiClient;
import edu.cnm.deepdive.codebreaker.ApiException;
import edu.cnm.deepdive.codebreaker.Configuration;
import edu.cnm.deepdive.codebreaker.models.*;
import edu.cnm.deepdive.codebreaker.service.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://ddc-java.services/codebreaker-solitaire");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String gameId = "gameId_example"; // String | 
        try {
            Game result = apiInstance.getGame(gameId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#getGame");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **gameId** | **String**|  | |

### Return type

[**Game**](Game.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Specified game returned. |  -  |
| **404** | Game not found. |  -  |


## getGuess

> Guess getGuess(gameId, guessId)

Retrieve a specific guess resource

### Example

```java
// Import classes:
import edu.cnm.deepdive.codebreaker.ApiClient;
import edu.cnm.deepdive.codebreaker.ApiException;
import edu.cnm.deepdive.codebreaker.Configuration;
import edu.cnm.deepdive.codebreaker.models.*;
import edu.cnm.deepdive.codebreaker.service.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://ddc-java.services/codebreaker-solitaire");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String gameId = "gameId_example"; // String | 
        String guessId = "guessId_example"; // String | 
        try {
            Guess result = apiInstance.getGuess(gameId, guessId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#getGuess");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **gameId** | **String**|  | |
| **guessId** | **String**|  | |

### Return type

[**Guess**](Guess.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Specified guess returned. |  -  |
| **404** | Game or Guess not found. |  -  |


## startGame

> Game startGame(game)

Start a new game

### Example

```java
// Import classes:
import edu.cnm.deepdive.codebreaker.ApiClient;
import edu.cnm.deepdive.codebreaker.ApiException;
import edu.cnm.deepdive.codebreaker.Configuration;
import edu.cnm.deepdive.codebreaker.models.*;
import edu.cnm.deepdive.codebreaker.service.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://ddc-java.services/codebreaker-solitaire");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        Game game = new Game(); // Game | 
        try {
            Game result = apiInstance.startGame(game);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#startGame");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **game** | [**Game**](Game.md)|  | |

### Return type

[**Game**](Game.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Code generated &amp; game started successfully. |  -  |
| **400** | Invalid code length or character pool. |  -  |


## submitGuess

> Guess submitGuess(gameId, guess)

Submit a new guess for a game

### Example

```java
// Import classes:
import edu.cnm.deepdive.codebreaker.ApiClient;
import edu.cnm.deepdive.codebreaker.ApiException;
import edu.cnm.deepdive.codebreaker.Configuration;
import edu.cnm.deepdive.codebreaker.models.*;
import edu.cnm.deepdive.codebreaker.service.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://ddc-java.services/codebreaker-solitaire");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String gameId = "gameId_example"; // String | 
        Guess guess = new Guess(); // Guess | 
        try {
            Guess result = apiInstance.submitGuess(gameId, guess);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#submitGuess");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **gameId** | **String**|  | |
| **guess** | [**Guess**](Guess.md)|  | |

### Return type

[**Guess**](Guess.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Guess submitted successfully. |  -  |
| **400** | Length of guess doesn’t match code length. |  -  |
| **404** | Game not found. |  -  |
| **409** | Game already solved. |  -  |

