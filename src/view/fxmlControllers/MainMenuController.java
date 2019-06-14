package view.fxmlControllers;

import controller.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Account;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.ResourceBundle;

import static view.Utility.*;

public class MainMenuController implements Initializable {
    public AnchorPane mainPane;

    public static Scene getScene() {
        return LoadedScenes.mainMenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void startGame(MouseEvent mouseEvent) {
    }

    public void logout(MouseEvent mouseEvent) {
        Account.saveAccounts();
        Account.setActiveAccount(null);
        Main.mainStage.setScene(LoadedScenes.registerMenu);
        Main.mainStage.setFullScreen(true);
    }

    public void collection(MouseEvent mouseEvent) {
    }

    public void shop(MouseEvent mouseEvent) {

    }
}