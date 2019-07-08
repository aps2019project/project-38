package view.fxmlControllers;

import client.Messages;
import client.net.ClientConnector;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    public void leatherBoard() {
        try {
            WindowChanger.instance.setMainParent(Utility.scale(FXMLLoader.load(LoadedScenes.class.getResource("leaderBoard.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        ClientConnector.printer.println(Messages.quitTheGame);
        System.exit(0);
    }
}