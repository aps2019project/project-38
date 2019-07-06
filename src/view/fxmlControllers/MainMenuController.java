package view.fxmlControllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Account;
import view.WindowChanger;
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
        if (Account.getActiveAccount().getCollection().getMainDeck() != null) {
            LoadingGamePreviewScenes.load();
        } else {
            AlertController.setAndShow("You have not a main deck");
        }
    }

    public void logout() {
        Account.saveAccounts();
        Account.setActiveAccount(null);
        WindowChanger.instance.setNewScene(LoadedScenes.registerMenu);
    }

    public void createDeck() {
        CollectionController.collectionController.calculateEveryThing();
        WindowChanger.instance.setNewScene(LoadedScenes.collection);
    }

    public void shop() {
        WindowChanger.instance.setNewScene(LoadedScenes.shop);
    }

    public void customCard_btn() {
        try {
            WindowChanger.instance.setNewScene(FXMLLoader.load(LoadedScenes.class.getResource("customCard.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
