package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import view.fxmlControllers.RegisterMenuController;

import java.io.IOException;

public class LoadedPanes {

    public static Pane registerMenu;
    public static Pane createAccount;
    public static Pane login;
    public static Pane leatherBoard;

    {
        try {
            registerMenu = FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/RegisterMenu.fxml"));
            createAccount = FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/createAccount.fxml"));
            login = FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/login.fxml"));
            leatherBoard = FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/leatherBoard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
