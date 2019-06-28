package view.fxmlControllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Account;
import view.WindowChanger;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterMenuController implements Initializable {
    public AnchorPane mainPane;
    public ImageView backGround;

    public static Pane getScene() {
        return LoadedScenes.registerMenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backGround.setImage(LoadedImages.backGroundOfRegisterMenu);
    }

    public void createAccount() {
        WindowChanger.instance.setNewScene(LoadedScenes.createAccount);

    }

    public void login() {
        WindowChanger.instance.setNewScene(LoadedScenes.login);

    }

    public void leatherBoard() {
        try {
            WindowChanger.instance.setNewScene(FXMLLoader.load(LoadedScenes.class.getResource("leaderBoard.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        Account.saveAccounts();
        System.exit(0);
    }
}