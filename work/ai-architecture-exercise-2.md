# Initial response

Building a dynamic UI in JavaFX that adapts to configuration files is a great way to keep your game logic decoupled from your presentation. Since FXML doesn't support loops (like a `foreach` tag) to generate multiple buttons, you’ll want to combine the **ResourceBundle** for data retrieval with the **Controller's initialization** for the UI construction.

Here is how you can map your symbols from a `.properties` file to a dynamic palette.

---

### 1. Define the Symbols in a Properties File

Create a file named `game_config.properties` in your resources folder. Store your symbols as a delimited string.

```properties
# You can swap these out for 'Red,Orange,Yellow...' without changing Java code
game.symbols=A,B,C,D,E,F
game.palette.title=Select a Symbol:

```

### 2. Set Up the FXML Container

In your FXML file, define the container (like a `FlowPane` or `HBox`) where the buttons will live. Give it an `fx:id`.

```xml
<VBox xmlns:fx="http://javafx.com/fxml" fx:controller="com.game.GameController">
    <Label text="%game.palette.title" />
    <FlowPane fx:id="paletteContainer" hgap="10" vgap="10" />
</VBox>

```

### 3. Load the Bundle and FXML

When you load your UI, you must pass the `ResourceBundle` to the `FXMLLoader`.

```java
ResourceBundle bundle = ResourceBundle.getBundle("game_config");
FXMLLoader loader = new FXMLLoader(getClass().getResource("codebreaker.fxml"), bundle);
Parent root = loader.load();

```

### 4. Dynamically Generate the Palette in the Controller

The `FXMLLoader` automatically injects the `ResourceBundle` into the controller if you define a field named `resources`.

```java
public class GameController {

    @FXML
    private FlowPane paletteContainer;

    @FXML
    private ResourceBundle resources; // Automatically injected by FXMLLoader

    @FXML
    public void initialize() {
        // 1. Grab the raw string from the properties file
        String symbolData = resources.getString("game.symbols");

        // 2. Split by comma
        String[] symbols = symbolData.split(",");

        // 3. Create buttons on the fly
        for (String symbol : symbols) {
            Button btn = new Button(symbol.trim());
            btn.getStyleClass().add("palette-button");
            
            // Define what happens when a player clicks a symbol
            btn.setOnAction(e -> handleSymbolSelection(symbol));

            // 4. Add to the UI container
            paletteContainer.getChildren().add(btn);
        }
    }

    private void handleSymbolSelection(String symbol) {
        System.out.println("Selected: " + symbol);
        // Add your game logic here
    }
}

```

---

### Why this works

* **Decoupled Logic:** If you want to change the game from letters (A-F) to colors (Red-Violet), you only edit the `.properties` file.
* **Injected Resources:** By using `@FXML private ResourceBundle resources`, you gain access to all configuration keys directly within the controller's lifecycle.
* **Scalability:** The `FlowPane` will automatically wrap the buttons to a new line if your properties file contains 20 symbols instead of 6.

### A Quick Note on Android

If you are using **Gluon Mobile** to run JavaFX on Android, this approach works perfectly. However, if you are moving toward **native Android (XML/Kotlin)**, you would replace `ResourceBundle` with `strings.xml` and use a `RecyclerView` or `GridLayout` to programmatically inflate views.

---

**Would you like me to show you how to map these symbols to specific CSS colors so the buttons change color based on the text value?**

# Asking it "How can I store the 'logical character' inside a JavaFX Button object using setUserData()?"

It tells me about  storing data directly in buttons, but not necessarily displayed.

Using `setUserData()` is the cleanest way to separate what the player **sees** on the button from what the game logic **processes**.

Think of `setUserData()` as a "hidden pocket" on every UI component. You can stuff any Object in there—a String, an Integer, or even a custom `GameSymbol` class—and pull it out later when the user clicks.

### 1. Attaching the Data

When you are generating your buttons in the `initialize()` method, simply call `setUserData(value)`.

```java
for (String symbol : symbols) {
    Button btn = new Button(symbol.trim());
    
    // Store the raw symbol string (or a char) as the "logical" value
    btn.setUserData(symbol.trim()); 
    
    btn.setOnAction(this::handleButtonClick);
    paletteContainer.getChildren().add(btn);
}

```

### 2. Retrieving the Data in the Event Handler

When the button is clicked, the `ActionEvent` contains a reference to the button. You just need to cast it and call `getUserData()`.

```java
private void handleButtonClick(ActionEvent event) {
    // 1. Get the source of the click
    Button clickedButton = (Button) event.getSource();

    // 2. Extract the hidden "logical character"
    // Note: getUserData returns an Object, so you must cast it back to String
    String logicalSymbol = (String) clickedButton.getUserData();

    // 3. Use it in your game logic
    System.out.println("The game logic is now processing: " + logicalSymbol);
    checkMove(logicalSymbol);
}

```

---

### Why use `setUserData()` instead of `getText()`?

While it’s tempting to just use `button.getText()`, using `setUserData()` is a professional "future-proofing" move for three reasons:

* **Localization:** Your button text might change to "Rojo" (Spanish), but your logical data stays `"RED"`.
* **Complex Data:** You can store an entire Object. For example, if your symbols have properties like `colorValue` or `pointValue`, you can store the whole object:
  `btn.setUserData(new GameSymbol("A", Color.RED, 10));`
* **UI Decoupling:** You can change the button's appearance (adding icons or clearing the text) without breaking the game logic.

### Alternative: The `Properties` Map

If you need to store *multiple* pieces of data on one button (e.g., the symbol value AND its grid position), JavaFX nodes also have a `getProperties()` map:

```java
// Setting multiple values
btn.getProperties().put("symbol", "A");
btn.getProperties().put("index", 5);

// Retrieving
String symbol = (String) btn.getProperties().get("symbol");

```

---

**Would you like to see how to implement the "Secret Code" logic to compare these retrieved symbols against a randomly generated winning sequence?**


# After asking "How can I store the name of an FXML file in my properties file and load that specific layout using the FXMLLoader?"

This is a clever way to handle "theming" or switching between different game modes (like a "Simple" vs. "Advanced" layout) without recompiling your code.

By storing the FXML path in your `ResourceBundle`, you treat the UI structure itself as just another configuration setting.

---

### 1. Update the Properties File

Add a key for the layout file. It’s best to use the path relative to your `resources` folder.

```properties
# config.properties
ui.layout.main=/view/game_board.fxml
ui.layout.mobile=/view/mobile_board.fxml

```

### 2. Load the Layout Dynamically

In your main application (or a "Scene Switcher" utility class), you pull the string from the bundle and convert it into a URL that the `FXMLLoader` understands.

```java
public void loadDynamicUI(Stage stage) throws IOException {
    // 1. Load the bundle
    ResourceBundle bundle = ResourceBundle.getBundle("config");

    // 2. Get the FXML filename string
    String fxmlPath = bundle.getString("ui.layout.main");

    // 3. Convert the string path to a URL
    // getClass().getResource() looks for the file in your classpath
    URL fxmlLocation = getClass().getResource(fxmlPath);

    if (fxmlLocation == null) {
        throw new IOException("FXML file not found at: " + fxmlPath);
    }

    // 4. Load it!
    FXMLLoader loader = new FXMLLoader(fxmlLocation, bundle);
    Parent root = loader.load();

    stage.setScene(new Scene(root));
    stage.show();
}

```

---

### Key Technical Details to Watch For

* **Leading Slashes:** If your properties file says `/view/main.fxml`, Java looks at the root of your resources. If you omit the slash (`view/main.fxml`), it looks relative to the package of the class calling `getResource()`. Always use a leading slash for predictability.
* **The Controller Factory:** If you are using this for different layouts that share the *same* logic, ensure both FXML files point to the same `fx:controller`. If they use different controllers, you may need to use `loader.getController()` to set up specific logic after loading.
* **Null Checks:** `getClass().getResource()` returns `null` if the path is slightly wrong (e.g., a typo in the `.properties` file). Always check for null before passing it to the `FXMLLoader` to avoid a `NullPointerException`.

### Why this is powerful for Android

Since you mentioned building for Android, this allows you to have a `tablet.properties` and a `phone.properties`. Your code stays identical, but the `ResourceBundle` decides whether to load a spacious `grid_layout.fxml` or a compact `scroll_layout.fxml`.

---

**Would you like me to show you how to detect the screen size at startup so your game automatically picks the correct FXML file from the properties?**


# Evaluation from rubric

Separation of Concerns: 5/5
Scalability: 5/5
Flexibility: 5/5
Robustness: 4/5 - did not fail gracefully, just threw exception.
