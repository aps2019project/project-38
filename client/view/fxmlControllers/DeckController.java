package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import model.Account;
import model.Deck;
import model.cards.Card;
import model.cards.Warrior;
import view.WindowChanger;
import view.fxmls.LoadedScenes;
import view.visualentities.VisualMinion;
import view.visualentities.VisualSpell;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class DeckController implements Initializable {

    public static String selectedDeck;
    public static Label previousClickedLabel;
    private static VBox staticDeckList;
    private static VBox[][] containers = new VBox[4][3];
    public TextField deckName;
    public TextField cardName;
    public VBox deckList;
    public VBox heroes1;
    public VBox heroes2;
    public VBox heroes3;
    public VBox minions1;
    public VBox minions2;
    public VBox minions3;
    public VBox spells1;
    public VBox spells2;
    public VBox spells3;
    public VBox items1;
    public VBox items2;
    public VBox items3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            for (Map.Entry<String, Deck> entry : Account.getActiveAccount().getCollection().getAllDecks().entrySet()) {
                putADeckToList(entry.getKey());
            }
        });
        staticDeckList = deckList;
        containers[0][0] = heroes1;
        containers[0][1] = heroes2;
        containers[0][2] = heroes3;
        containers[1][0] = minions1;
        containers[1][1] = minions2;
        containers[1][2] = minions3;
        containers[2][0] = spells1;
        containers[2][1] = spells2;
        containers[2][2] = spells3;
        containers[3][0] = items1;
        containers[3][1] = items2;
        containers[3][2] = items3;
    }

    public void back() {
        WindowChanger.instance.setMainParent(LoadedScenes.mainMenu);
    }

    public void createDeck() {
        Account.getActiveAccount().getCollection().createDeck(deckName.getText());
    }

    public void deleteDeck() {
        Account.getActiveAccount().getCollection().deleteDeck(deckName.getText());
        if (deckName.getText().equals(selectedDeck)) {
            reOrderADeck(null);
        }
    }

    public void validateDeck() {
        Account.getActiveAccount().getCollection().validateDeck(deckName.getText(), true);
    }

    public void selectMainDeck() {
        Account.getActiveAccount().getCollection().selectMainDeck(deckName.getText());
    }

    public void addCard() {
        Account.getActiveAccount().getCollection().addCardToDeck(cardName.getText(), deckName.getText());
        if (selectedDeck.equals(deckName.getText())) {
            reOrderADeck(deckName.getText());
        }
    }

    public void removeCard() {
        Account.getActiveAccount().getCollection().removeCardFromDeck(cardName.getText(), deckName.getText());
        if (selectedDeck.equals(deckName.getText())) {
            reOrderADeck(deckName.getText());
        }
    }

    public static void putADeckToList(String name) {
        Label label = new Label(name);
        label.setFont(Font.font(20));
        label.setPrefWidth(200);
        label.setPrefHeight(30);
        label.setAlignment(Pos.CENTER);
        VBox.setMargin(label, new Insets(0, 5, 5, 5));
        staticDeckList.getChildren().add(label);
        label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        label.setOnMouseClicked(event -> {
            selectedDeck = name;
            if (previousClickedLabel != null) {
                previousClickedLabel.setEffect(null);
            }
            previousClickedLabel = label;
            label.setEffect(new DropShadow());
            reOrderADeck(name);
        });
    }

    public static void removeADeckFromList(String name) {
        for (Node label : staticDeckList.getChildren()) {
            if (((Label) label).getText().equals(name)) {
                staticDeckList.getChildren().remove(label);
                break;
            }
        }
    }

    private static void reOrderADeck(String deckName) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                containers[i][j].getChildren().clear();
            }
        }
        if (deckName == null) return;

        Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(deckName);
        if (deck.getHero() != null) {
            containers[0][0].getChildren().add(getCardHolder(deck.getHero(), true));
        }
        int cnt = 0;
        for (Card card : deck.minions) {
            containers[1][cnt % 3].getChildren().add(getCardHolder(card, true));
            cnt++;
        }
        cnt = 0;
        for (Card card : deck.spells) {
            containers[2][cnt % 3].getChildren().add(getCardHolder(card, false));
            cnt++;
        }
        if (deck.getItem() != null) {
            containers[3][0].getChildren().add(getCardHolder(deck.getItem(), false));
        }
    }

    private static Pane getCardHolder(Card card, boolean isWarrior) {
        Pane pane = null;
        FXMLLoader fxmlLoader;
        try {
            if (isWarrior) {
                fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("shownWarriorInArena.fxml"));
                pane = fxmlLoader.load();
                ShownWarriorInArenaController shownWarriorInArenaController = fxmlLoader.getController();
                shownWarriorInArenaController.setName(card.getName());
                shownWarriorInArenaController.setDescription(card.description.getDescriptionOfCardSpecialAbility());
                shownWarriorInArenaController.setType(card.description.getTargetType());
                shownWarriorInArenaController.setAP(((Warrior) card).getAp());
                shownWarriorInArenaController.setHP(((Warrior) card).getHp());
                VisualMinion vm = new VisualMinion(card.getName());
                shownWarriorInArenaController.put(vm.view, vm.getWidth(), vm.getHeight());
            } else {
                fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("shownSpellInArena.fxml"));
                pane = fxmlLoader.load();
                ShownSomethingInArenaController shownSomethingInArenaController = fxmlLoader.getController();
                shownSomethingInArenaController.setName(card.getName());
                shownSomethingInArenaController.setDescription(card.description.getDescriptionOfCardSpecialAbility());
                shownSomethingInArenaController.setType(card.description.getTargetType());
                VisualSpell vs = new VisualSpell(card.getName());
                shownSomethingInArenaController.put(vs.view, vs.getWidth(), vs.getHeight());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scalePane(pane);
    }

    private static Pane scalePane(Pane pane) {
        Scale scale = new Scale((double) 240 / 160, (double) 240 / 160);
        pane.getTransforms().add(scale);
        VBox.setMargin(pane, new Insets(0, 0, 100, 0));
        return pane;
    }
}
