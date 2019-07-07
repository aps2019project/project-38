package view.fxmlControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Account;
import view.Utility;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;

public class RegisterMenuController {
    public AnchorPane mainPane;
    public ImageView backGround;

    public static Pane getScene() {
        return LoadedScenes.registerMenu;
    }

    public void createAccount() {
        WindowChanger.instance.setMainParent(LoadedScenes.createAccount);

    }

    public void login() {
        WindowChanger.instance.setMainParent(LoadedScenes.login);

    }

    public void leatherBoard() {
        try {
            WindowChanger.instance.setMainParent(Utility.scale(FXMLLoader.load(LoadedScenes.class.getResource("leaderBoard.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        Account.saveAccounts();
        System.exit(0);
    }
}