package view.fxmlControllers;

import controller.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import model.Account;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public AnchorPane mainPane;

    public static Scene getScene() {
        return LoadedScenes.mainMenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void startGame() {
        Main.mainStage.setScene(LoadedScenes.chooseBattleKind);
        Main.mainStage.setFullScreen(true);
    }

    public void logout() {
        Account.saveAccounts();
        Account.setActiveAccount(null);
        Main.mainStage.setScene(LoadedScenes.registerMenu);
        Main.mainStage.setFullScreen(true);
    }

    public void collection() {
    }

    public void shop() {

    }
}
