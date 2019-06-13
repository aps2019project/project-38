package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import view.fxmlControllers.RegisterMenuController;

import java.io.IOException;

public class LoadedScenes {

    public static Scene registerMenu;
    public static Scene createAccount;
    public static Scene login;
    public static Scene leatherBoard;
    public static Scene mainMenu;
    public static Scene shop;
    public static Scene collectionOfShop;

    {
        try {
            registerMenu = new Scene(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/registerMenu.fxml")));
            createAccount = new Scene(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/createAccount.fxml")));
            login = new Scene(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/login.fxml")));
            leatherBoard = new Scene(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/leatherBoard.fxml")));
            mainMenu = new Scene(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/mainMenu.fxml")));
            shop = new Scene(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/shop.fxml")));
            collectionOfShop = new Scene(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/collectionOfShop.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
