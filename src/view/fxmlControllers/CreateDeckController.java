package view.fxmlControllers;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import model.Account;
import model.cards.Hero;
import model.cards.Spell;
import model.cards.Warrior;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class CreateDeckController implements Initializable {

    public TextField deckName;
    public TextField cardName;
    private static VBox staticDeckList;
    public VBox deckList;
    private VBox[][] containers = new VBox[4][3];
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
//        int cnt = 0;
//        for (Map.Entry<Hero, AnchorPane> tmp : ShopController.heroes.entrySet()) {
//            containers[0][cnt % 3].getChildren().add(scaleCard(tmp.getValue()));
//            cnt++;
//        }
//        cnt = 0;
//        for (Map.Entry<Warrior, AnchorPane> tmp : ShopController.minions.entrySet()) {
//            containers[1][cnt % 3].getChildren().add(scaleCard(tmp.getValue()));
//            cnt++;
//        }
//        cnt = 0;
//        for (Map.Entry<Spell, AnchorPane> tmp : ShopController.spells.entrySet()) {
//            containers[2][cnt % 3].getChildren().add(scaleCard(tmp.getValue()));
//            cnt++;
//        }
//        cnt = 0;
//        for (Map.Entry<Spell, AnchorPane> tmp : ShopController.items.entrySet()) {
//            containers[3][cnt % 3].getChildren().add(scaleCard(tmp.getValue()));
//            cnt++;
//        }
    }

    public void back() {
        WindowChanger.instance.setNewScene(LoadedScenes.mainMenu);
    }

    public void createDeck() {
        Account.getActiveAccount().getCollection().createDeck(deckName.getText());
    }

    public void deleteDeck() {
        Account.getActiveAccount().getCollection().deleteDeck(deckName.getText());
    }

    public void validateDeck() {
        Account.getActiveAccount().getCollection().validateDeck(deckName.getText());
    }

    public void selectMainDeck() {
        Account.getActiveAccount().getCollection().selectMainDeck(deckName.getText());
    }

    public void addCard() {
        Account.getActiveAccount().getCollection().addCardToDeck(deckName.getText(),cardName.getText());
    }

    public void removeCard() {
        Account.getActiveAccount().getCollection().removeCardFromDeck(deckName.getText(),cardName.getText());
    }

    public static void putANewDeckToList(String name) {
        Label label = new Label(name);
        label.setFont(Font.font(20));
        label.resize(182, 32);// todo
        label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        label.setAlignment(Pos.CENTER);
        staticDeckList.getChildren().add(label);
    }

    public static void removeADeckFromList(String name) {
        for (Node label : staticDeckList.getChildren()) {
            if (((Label) label).getText().equals(name)) {
                staticDeckList.getChildren().remove(label);
                break;
            }
        }
    }

    private AnchorPane scaleCard(AnchorPane anchorPane) {
        Scale scale = new Scale((double) 240 / 394, (double) 240 / 394);
        anchorPane.getTransforms().add(scale);
        return anchorPane;
    }
}
