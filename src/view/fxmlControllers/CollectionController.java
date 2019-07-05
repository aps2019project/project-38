package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
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
    public TextField decksSearchTextField;
    public ImageView renameSelectedDeckButton;
    public Text renameSelectedDeckText;
    public ImageView removeSelectedDeckButton;
    public Text removeSelectedDeckText;
    public ImageView selectedDeckButton;
    public Text selectedDeckText;
    private static HashMap<Deck, AnchorPane> decks = new HashMap<>(), allDecks = new HashMap<>();
    public VBox decksLeftVBox;
    public VBox decksRightVBox;
    private HashMap<Deck, String> deckToTypeHashMap;
    private HashMap<Deck, DeckButtonController> deckToDeckButtonControllerHashMap = new HashMap<>();
    private Deck selectedDeck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collectionController = this;
        decksSearchTextField.setOnKeyTyped(collectionController::recalculateDecks);
    }

    public void renameSelectedDeck(MouseEvent mouseEvent) {
        if (selectedDeck != null)
            if (Collection.getCollection().renameDeck(selectedDeck.getName(), decksSearchTextField.getText())) {
                selectedDeck.setName(decksSearchTextField.getText());
                calculateEveryThing();
            }
        else AlertController.setAndShowAndGetResultByAnAlertController("Select a deck", false);
    }

    public void shineRenameSelectedDeckButton(MouseEvent mouseEvent) {
        renameSelectedDeckButton.setEffect(new DropShadow());
        renameSelectedDeckText.setEffect(new DropShadow());
    }

    public void resetRenameSelectedDeckButton(MouseEvent mouseEvent) {
        renameSelectedDeckButton.setEffect(null);
        renameSelectedDeckText.setEffect(null);
    }

    public void removeSelectedDeck(MouseEvent mouseEvent) {
        if (selectedDeck != null)
            if (deckToTypeHashMap.get(selectedDeck).equals("Main Deck")) {
                AlertController alertController = AlertController.setAndShowAndGetResultByAnAlertController
                        ("This is your main deck. Are you sure about removing it?", true);
                new Thread(() -> {
                    synchronized (alertController) {
                        try {
                            alertController.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (alertController.result) {
                            if (Collection.getCollection().deleteDeck(selectedDeck.getName())) {
                                selectedDeck.setName(decksSearchTextField.getText());
                                calculateEveryThing();
                            }
                        }
                    }
                }).start();
            }
            else if (Collection.getCollection().deleteDeck(selectedDeck.getName())) {
                selectedDeck.setName(decksSearchTextField.getText());
                calculateEveryThing();
            }
        else AlertController.setAndShowAndGetResultByAnAlertController("Select a deck", false);
    }

    public void shineRemoveSelectedDeckButton(MouseEvent mouseEvent) {
        removeSelectedDeckButton.setEffect(new DropShadow());
        renameSelectedDeckText.setEffect(new DropShadow());
    }

    public void resetRemoveSelectedDeckButton(MouseEvent mouseEvent) {
        removeSelectedDeckButton.setEffect(null);
        renameSelectedDeckText.setEffect(null);
    }

    public void enterChoosingDeckCards(MouseEvent mouseEvent) {
        if (selectedDeck != null)
            if (deckToTypeHashMap.get(selectedDeck).equals("Main Deck")) {
                AlertController alertController = AlertController.setAndShowAndGetResultByAnAlertController
                        ("This is your main deck and changing it will reset it. Are you sure about changing it?",
                                true);
                new Thread(() -> {
                    synchronized (alertController) {
                        try {
                            alertController.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (alertController.result) {
                            Collection.getCollection().setMainDeck(null);
                            ChoosingDeckCardsController.choosingDeckCardsController.calculateEveryThing(selectedDeck);
                            WindowChanger.instance.setNewScene(LoadedScenes.choosingDeckCards);
                        }
                    }
                }).start();
            } else {
                ChoosingDeckCardsController.choosingDeckCardsController.calculateEveryThing(selectedDeck);
                WindowChanger.instance.setNewScene(LoadedScenes.choosingDeckCards);
            }
        else AlertController.setAndShowAndGetResultByAnAlertController("Select a deck", false);
    }

    public void shineSelectedDeckButton(MouseEvent mouseEvent) {
        selectedDeckButton.setEffect(new Bloom(0));
        selectedDeckText.setEffect(new Bloom(0));
    }

    public void resetSelectedDeckButton(MouseEvent mouseEvent) {
        selectedDeckButton.setEffect(null);
        selectedDeckText.setEffect(null);
    }

    public void calculateEveryThing() {
        deckToTypeHashMap = new HashMap<>();
        for (Deck deck : Collection.getCollection().getAllDecks().values()) {
            if (Collection.getCollection().getMainDeck() == deck) {
                deckToTypeHashMap.put(deck, "Main Deck");
            } else if (Collection.getCollection().validateDeck(deck.getName(), false)) {
                deckToTypeHashMap.put(deck, "Ready");
            } else {
                deckToTypeHashMap.put(deck, "Not Ready");
            }
        }
        initializeAllDecks();
        recalculateDecks(null);
    }

    private void initializeAllDecks() {
        allDecks.entrySet().removeIf(entry -> !deckToTypeHashMap.containsKey(entry.getKey()));
        deckToDeckButtonControllerHashMap.entrySet().removeIf(entry -> !allDecks.containsKey(entry.getKey()));
        for (Map.Entry<Deck, String> entry : deckToTypeHashMap.entrySet()) {
            if (!allDecks.containsKey(entry.getKey())) {
                loadDeckButton(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<Deck, DeckButtonController> entry : deckToDeckButtonControllerHashMap.entrySet()) {
            entry.getValue().setFields(entry.getKey(), deckToTypeHashMap.get(entry.getKey()));
        }
    }

    private void loadDeckButton(Deck deck, String type) {
        AnchorPane anchorPane = null;
        DeckButtonController deckButtonController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("DeckButtonController.java"));
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
            }
            else {
                Platform.runLater(() -> decksRightVBox.getChildren().add(entry.getValue()));
            }
            counter++;
        }
    }

    public void selectDeck(Deck deck) {
        selectedDeck = deck;
        selectedDeckText.setText(deck.getName() + ": " + deckToTypeHashMap.get(deck));
    }
}
