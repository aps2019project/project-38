package view.fxmlControllers;

import client.net.ClientSession;
import client.net.Encoder;
import client.net.Message;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Account;
import view.WindowChanger;
import view.fxmlControllers.AlertController;
import view.fxmlControllers.CollectionController;
import view.fxmlControllers.LoadingGamePreviewScenes;
import view.fxmlControllers.ShopController;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public AnchorPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void startGame() {
        Encoder.sendMessage(Message.startTheGame);
        Encoder.sendString(Account.activeAccount.username);

        int messageIndex = 0;

        try {
            messageIndex = ClientSession.dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Message.accountDeckIsValid.equals(Message.values()[messageIndex])) {
            LoadingGamePreviewScenes.load();
        } else {
            AlertController.setAndShow("You have not a main deck");
        }
    }

    public void logout() {
        Encoder.sendMessage(Message.logOut);
        Encoder.sendString(Account.activeAccount.username);
        Account.activeAccount.authToken = "";
        Account.activeAccount.username = null;
        WindowChanger.instance.setMainParent(LoadedScenes.registerMenu);
    }

    public void createDeck() {
        CollectionController.collectionController.calculateEveryThing();
        WindowChanger.instance.setMainParent(LoadedScenes.collection);
    }

    public void shop() {
        ShopController.shopController.calculateEverything();
        WindowChanger.instance.setMainParent(LoadedScenes.shop);
    }

    public void customCard_btn() {
        WindowChanger.instance.setMainParent(LoadedScenes.customCard);
    }


    public void globalChat_btn() {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(LoadedScenes.class.getResource("globalChat.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WindowChanger.instance.setMainParent(pane);
    }
}