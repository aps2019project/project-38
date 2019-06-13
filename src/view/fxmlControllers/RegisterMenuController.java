package view.fxmlControllers;

import controller.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Account;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

import java.net.URL;
import java.util.ResourceBundle;

import static view.Utility.*;

public class RegisterMenuController implements Initializable {
    private static Scene scene = null;
    public AnchorPane mainPane;
    public ImageView backGround;

    public static Scene getScene() {
        if (scene == null) {
            scene = new Scene(LoadedScenes.registerMenu, 480, 270);
        }
        return scene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane = (AnchorPane) scale(mainPane);
        backGround.setImage(LoadedImages.backGroundOfRegisterMenu);
    }

    public void createAccount() {
        Main.mainStage.setScene(CreateAccountController.getScene());
        Main.mainStage.setFullScreen(true);
    }

    public void login() {
        Main.mainStage.setScene(LoginController.getScene());
        Main.mainStage.setFullScreen(true);
    }

    public void leatherBoard() {
        Main.mainStage.setScene(LeatherBoardController.getScene());
        Main.mainStage.setFullScreen(true);
    }

    public void exit() {
        Account.saveAccounts();
        System.exit(0);
    }
}