package view.fxmlControllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

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
}
