package view.fxmlControllers;

import controller.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.cards.Card;
import model.cards.CardFactory;
import model.cards.Warrior;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShopController implements Initializable {
    public ImageView background;
    public ScrollPane scrollPane;
    public HBox hBox;
    public VBox leftVBox;
    public VBox rightVBox;
    public ImageView goldCircleOfCollectionButton;
    public ImageView backButton;
    public ImageView collectionButton;
    public Text collectionText;
    public ImageView textFiledImage;
    public TextField searchTextFiled;
    private HashMap<Card, AnchorPane> cards = new HashMap<>();

    public void moveScrollPane(KeyEvent keyEvent) {
    }

    public void scrollScrollPane(ScrollEvent scrollEvent) {
    }

    public void back(MouseEvent mouseEvent) {
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow(0.5));
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    public void goToCollection(MouseEvent mouseEvent) {
        Main.mainStage.setScene(LoadedScenes.collectionOfShop);
        Main.mainStage.setFullScreen(true);
    }

    public void shineCollectionBottom(MouseEvent mouseEvent) {
        collectionButton.setEffect(new Glow(0.5));
        goldCircleOfCollectionButton.setOpacity(0.9);
        collectionText.setOpacity(1);
    }

    public void resetCollectionBottom(MouseEvent mouseEvent) {
        collectionButton.setEffect(null);
        goldCircleOfCollectionButton.setOpacity(0.6);
        collectionText.setOpacity(0.6);
    }

    public void recalculateCards(KeyEvent keyEvent) {
        //InputMethodEvent inputMethodEvent
        String searchText = searchTextFiled.getText();
        cards.entrySet().removeIf(entry -> !entry.getKey().getName().matches(searchText + ".*"));
        for (Warrior minion : CardFactory.getAllBuiltMinions()) {
            if (!cards.containsKey(minion) && minion.getName().matches(searchText + ".*")) addNewMinion(minion);
        }
    }

    private void addNewMinion(Warrior minion) {
        AnchorPane anchorPane = null;
        WarriorCardController warriorCardController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("warriorCart.fxml"));
            anchorPane = fxmlLoader.load();
            System.out.println(anchorPane);
            warriorCardController = fxmlLoader.getController();
            System.out.println(warriorCardController);
        } catch (IOException e) {
            e.printStackTrace();
        }
        warriorCardController.setFields(minion);
        cards.put(minion, anchorPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTextFiled.setOnKeyTyped(this::recalculateCards);
        Platform.runLater(() -> {
            recalculateCards(null);
        });
    }
}
