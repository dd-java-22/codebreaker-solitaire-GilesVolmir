package edu.cnm.deepdive.codebreaker.controller;

import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.viewmodel.GameViewModel;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MainController {

  private static final String PROPERTIES_FILE = "game.properties";
  private static final String POOL_KEY = "pool";
  private static final String LENGTH_KEY = "length";
  private static final Pattern PROPERTY_LIST_DELIMITER = Pattern.compile("\\s*,\\s*");

  @FXML
  private ResourceBundle resources; //needs to be THIS NAME EXACTLY to be injected by FXMLloader.
//  @FXML
//  private URL location; //this is the other autoinjected option. comes from FXML loader args.

  @FXML
  private ScrollPane scrollPane;
  @FXML
  private TextFlow textFlow;
  @FXML
  private Text gameState;
  @FXML
  private TilePane guessPalette;
  @FXML
  private Button send;

  private GameViewModel viewModel;
  private Game game;
  private Map<Integer, String> codePointNames;
  private Map<Integer, String> codePointClasses;

  @FXML
  private void initialize() throws IOException {
    buildCodePointMaps();

    viewModel = connectToViewModel();
    startGame();
  }

  private void buildCodePointMaps() {
    List<String> poolNames = buildPoolMap("pool_names");
    List<String> poolClasses = buildPoolMap("pool_classes");
    List<Integer> poolCodePoints = resources
        .getString(POOL_KEY)
        .codePoints()
        .boxed()
        .toList();

    codePointNames = new LinkedHashMap<>();
    codePointClasses = new LinkedHashMap<>();

    Iterator<Integer> codePointIter = poolCodePoints.iterator();
    Iterator<String> nameIter = poolNames.iterator();
    Iterator<String> classIter = poolClasses.iterator();
    while (codePointIter.hasNext() && nameIter.hasNext() && classIter.hasNext()) {

      Integer codePoint = codePointIter.next();
      codePointNames.put(codePoint, nameIter.next());
      codePointClasses.put(codePoint, classIter.next());
    }
  }

  private List<String> buildPoolMap(String key) {
    return PROPERTY_LIST_DELIMITER
        .splitAsStream(resources.getString(key))
        .filter(Predicate.not(String::isEmpty))
        .toList();
  }

  @FXML
  private void submitGuess() {
//    String guessText = guessInput.getText().strip();
//    if (guessText.length() == game.getLength()) {
//      viewModel.submitGuess(guessText);
  }


  private GameViewModel connectToViewModel() {
    GameViewModel viewModel = GameViewModel.getInstance();
    viewModel.registerGameObserver(this::handleGameUpdate);
    viewModel.registerErrorObserver(throwable -> {/* TODO display or log this throwable */});
    return viewModel;
  }

  private void handleGameUpdate(Game game) {
    this.game = game;
    gameState.setText(game.toString());
    ObservableList<Node> guessChildren = guessPalette.getChildren();
    guessChildren.clear();
    URL layoutUrl = getClass()
        .getClassLoader()
        .getResource(resources.getString("palette_item_layout"));

    codePointClasses
        .entrySet()
        .stream()
        .map((entry) -> {
          try {
            Node root = new FXMLLoader(layoutUrl, resources)
                .load();
            root.getStyleClass().add(entry.getValue());
            return root;
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        })
        .forEach(guessChildren::add);
  }


  private void startGame() throws IOException {
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
      Properties properties = new Properties();
      properties.load(input);
      String pool = properties.getProperty(POOL_KEY);
      int length = Integer.parseInt(properties.getProperty(LENGTH_KEY));
      viewModel.startGame(pool, length);
    }
  }
}
