package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Collection;
import model.Deck;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {
    public static CollectionController collectionController;
    private static HashMap<Deck, AnchorPane> decks = new HashMap<>(), allDecks = new HashMap<>();

    public ImageView renameSelectedDeckButton;
    public ImageView removeSelectedDeckButton;
    public ImageView selectedDeckButton;
    public ImageView decksSearchTextFieldImage;
    public ImageView backButtonImage;
    public Text renameSelectedDeckText;
    public Text removeSelectedDeckText;
    public Text selectedDeckText;
    public TextField decksSearchTextField;
    public VBox decksRightVBox;
    public VBox decksLeftVBox;
    private Deck selectedDeck;
    private HashMap<Deck, String> deckToTypeHashMap;
    private HashMap<Deck, DeckButtonController> deckToDeckButtonControllerHashMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decksSearchTextField.setOnKeyTyped(this::recalculateDecks);
    }


    public void renameSelectedDeck() {
        if (selectedDeck != null) {
            if (!decksSearchTextField.getText().equals("")) {
                if (Collection.getCollection().renameDeck(selectedDeck.getName(), decksSearchTextField.getText())) {
                    calculateEveryThing();
                }
            } else AlertController.setAndShow("Enter a deck name");
        } else AlertController.setAndShow("Select a deck");
    }

    public void createDeck(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            if (!decksSearchTextField.getText().equals("")) {
                AlertController.setAndShowAndDo
                        (String.format("Do you want to create a deck with name %s", decksSearchTextField.getText()), () -> {
                            if (Collection.getCollection().createDeck(decksSearchTextField.getText())) {
                                calculateEveryThing();
                            }
                        });
            } else AlertController.setAndShow("Enter deck name");
        }
    }

    public void removeSelectedDeck() {
        if (selectedDeck != null) {
            if (deckToTypeHashMap.get(selectedDeck).equals("ClientInit Deck")) {
                AlertController.setAndShowAndDo
                        ("This is your main deck. Are you sure about removing it?", () -> {
                            if (Collection.getCollection().deleteDeck(selectedDeck.getName())) {
                                selectedDeck = null;
                                calculateEveryThing();
                            }
                        });
            } else if (Collection.getCollection().deleteDeck(selectedDeck.getName())) {
                selectedDeck = null;
                calculateEveryThing();
            }
        } else AlertController.setAndShow("Select a deck");
    }

    public void enterChoosingDeckCards() {
        if (selectedDeck != null) {
            if (deckToTypeHashMap.get(selectedDeck).equals("ClientInit Deck")) {
                AlertController.setAndShowAndDo
                        ("This is your main deck and changing it will reset it. Are you sure about changing it?",
                                () -> {
                                    Collection.getCollection().setMainDeck(null);
                                    ChoosingDeckCardsController.choosingDeckCardsController.calculateEveryThing(selectedDeck);
                                    WindowChanger.instance.setMainParent(LoadedScenes.choosingDeckCards);
                                });
            } else {
                ChoosingDeckCardsController.choosingDeckCardsController.calculateEveryThing(selectedDeck);
                WindowChanger.instance.setMainParent(LoadedScenes.choosingDeckCards);
            }
        } else AlertController.setAndShow("Select a deck");
    }

    private void initializeAllDecks() {
        allDecks.entrySet().removeIf(entry -> !deckToTypeHashMap.containsKey(entry.getKey()));
        deckToDeckButtonControllerHashMap.entrySet().removeIf(entry -> !deckToTypeHashMap.containsKey(entry.getKey()));
        for (Map.Entry<Deck, String> entry : deckToTypeHashMap.entrySet()) {
            if (!allDecks.containsKey(entry.getKey())) {
                loadDeckButton(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<Deck, DeckButtonController> entry : deckToDeckButtonControllerHashMap.entrySet()) {
            entry.getValue().setFields(entry.getKey(), deckToTypeHashMap.get(entry.getKey()));
        }
    }

    public void calculateEveryThing() {
        deckToTypeHashMap = new HashMap<>();
        for (Deck deck : Collection.getCollection().getAllDecks().values()) {
            if (Collection.getCollection().getMainDeck() == deck) {
                deckToTypeHashMap.put(deck, "ClientInit Deck");
            } else if (Collection.getCollection().validateDeck(deck.getName(), false)) {
                deckToTypeHashMap.put(deck, "Ready");
            } else {
                deckToTypeHashMap.put(deck, "Not Ready");
            }
        }
        initializeAllDecks();
        recalculateDecks(null);
        selectedDeckText.setText(selectedDeck == null ?
                "Select A Deck" : selectedDeck.getName() + ": " + deckToTypeHashMap.get(selectedDeck));
    }

    private void loadDeckButton(Deck deck, String type) {
        AnchorPane anchorPane = null;
        DeckButtonController deckButtonController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("DeckButton.fxml"));
            anchorPane = fxmlLoader.load();
            double scaleDouble = 480d / anchorPane.getPrefWidth();
            anchorPane.getTransforms().add(new Scale(scaleDouble, scaleDouble, 0, 0));
            deckButtonController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        deckButtonController.setFields(deck, type);
        allDecks.put(deck, anchorPane);
        deckToDeckButtonControllerHashMap.put(deck, deckButtonController);
    }

    private synchronized void recalculateDecks(KeyEvent keyEvent) {
        String searchText = decksSearchTextField.getText();
        decks.entrySet().removeIf(entry -> !allDecks.containsKey(entry.getKey()));
        decks.entrySet().removeIf(entry -> !entry.getKey().getName().toLowerCase()
                .replaceAll("[ \t\\-_]+", "").matches
                        (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"));
        for (Map.Entry<Deck, AnchorPane> entry : allDecks.entrySet()) {
            if (!decks.containsKey(entry.getKey()) && entry.getKey().getName().toLowerCase()
                    .replaceAll("[ \t\\-_]+", "").matches
                            (".*" + searchText.toLowerCase().replaceAll("[ \t\\-_]+", "") + ".*"))
                decks.put(entry.getKey(), entry.getValue());
        }
        Platform.runLater(() -> {
            decksLeftVBox.getChildren().clear();
            decksRightVBox.getChildren().clear();
        });
        int counter = 0;
        for (Map.Entry<Deck, AnchorPane> entry : decks.entrySet()) {
            if (counter % 2 == 0) {
                Platform.runLater(() -> decksLeftVBox.getChildren().add(entry.getValue()));
            } else {
                Platform.runLater(() -> decksRightVBox.getChildren().add(entry.getValue()));
            }
            counter++;
        }
    }

    public void selectDeck(Deck deck) {
        selectedDeck = deck;
        selectedDeckText.setText(deck.getName() + ": " + deckToTypeHashMap.get(deck));
    }

    public void back() {
        WindowChanger.instance.setMainParent(LoadedScenes.mainMenu);
    }

    //---------------------------

    public void shineRenameSelectedDeckButton() {
        renameSelectedDeckButton.setEffect(new Bloom(0.4));
        renameSelectedDeckText.setEffect(new Bloom(0));
    }

    public void resetRenameSelectedDeckButton() {
        renameSelectedDeckButton.setEffect(null);
        renameSelectedDeckText.setEffect(null);
    }

    public void shineRemoveSelectedDeckButton() {
        removeSelectedDeckButton.setEffect(new Bloom(0.4));
        removeSelectedDeckText.setEffect(new Bloom(0));
    }

    public void resetRemoveSelectedDeckButton() {
        removeSelectedDeckButton.setEffect(null);
        removeSelectedDeckText.setEffect(null);
    }

    public void shineSelectedDeckButton() {
        selectedDeckButton.setEffect(new Bloom(0));
        selectedDeckText.setEffect(new Bloom(0));
    }

    public void resetSelectedDeckButton() {
        selectedDeckButton.setEffect(null);
        selectedDeckText.setEffect(null);
    }

    public void shineSearchTextField() {
        decksSearchTextFieldImage.setEffect(new Bloom(0.5));
    }

    public void resetSearchTextField() {
        decksSearchTextFieldImage.setEffect(null);
    }

    public void shineBackButton() {
        backButtonImage.setEffect(new Glow(1));
    }

    public void resetBackButton() {
        backButtonImage.setEffect(null);
    }
}
