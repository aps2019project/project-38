package view.fxmlControllers;

import controller.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Account;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public AnchorPane mainPane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void startGame() {
//        WindowChanger.instance.setNewScene(LoadedScenes.chooseBattleKind);
        LoadingGamePreviewScenes.load();
    }

    public void logout() {
        Account.saveAccounts();
        Account.setActiveAccount(null);
        WindowChanger.instance.setNewScene(LoadedScenes.registerMenu);
    }

    public void collection() {
    }

    public void shop() {
        WindowChanger.instance.setNewScene(LoadedScenes.shop);
    }
}
