package view.fxmlControllers;

import controller.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Account;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static view.Utility.scale;

public class RegisterMenuController implements Initializable {
    public AnchorPane mainPane;
    public ImageView backGround;

    public static Scene getScene() {
        return LoadedScenes.registerMenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backGround.setImage(LoadedImages.backGroundOfRegisterMenu);
    }

    public void createAccount() {
        Main.mainStage.setScene(LoadedScenes.createAccount);
        Main.mainStage.setFullScreen(true);
    }

    public void login() {
        Main.mainStage.setScene(LoadedScenes.login);
        Main.mainStage.setFullScreen(true);
    }

    public void leatherBoard() {
        try {
            Main.mainStage.setScene(new Scene(scale(FXMLLoader.load(LoadedScenes.class.getResource("leaderBoard.fxml")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.mainStage.setFullScreen(true);
    }

    public void exit() {
        Account.saveAccounts();
        System.exit(0);
    }
}