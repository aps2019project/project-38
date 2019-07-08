package view.fxmlControllers;

import client.net.Message;
import client.net.Encoder;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import view.Utility;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;

public class RegisterMenuController {
    public AnchorPane mainPane;
    public ImageView backGround;

    public void createAccount() {
        WindowChanger.instance.setMainParent(LoadedScenes.createAccount);
    }

    public void login() {
        WindowChanger.instance.setMainParent(LoadedScenes.login);
    }

    public void leaderBoard() {
        try {
            WindowChanger.instance.setMainParent(Utility.scale(FXMLLoader.load(LoadedScenes.class.getResource("leaderBoard.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        Encoder.sendCode(Message.quitTheGame);
        System.exit(0);
    }
}