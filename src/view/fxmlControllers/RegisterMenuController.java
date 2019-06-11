package view.fxmlControllers;

import controller.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import view.fxmls.LoadedPanes;
import view.images.LoadedImages;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterMenuController implements Initializable {

    public ImageView backGround;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("here");
        backGround.setImage(LoadedImages.backGroundOfRegisterMenu);
    }

    public void createAccount() {
        Scene scene = new Scene(LoadedPanes.createAccount, 480, 270);
        Main.mainStage.setScene(scene);
    }

    public void login() {
        Scene scene = new Scene(LoadedPanes.login, 480, 270);
        Main.mainStage.setScene(scene);
    }

    public void leatherBoard() {
        Scene scene = new Scene(LoadedPanes.leatherBoard, 480, 270);
        Main.mainStage.setScene(scene);
    }

    public void exit() {
        System.exit(0);
    }


}