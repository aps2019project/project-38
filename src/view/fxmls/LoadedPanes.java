package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.Utility;
import view.fxmlControllers.RegisterMenuController;

import java.io.IOException;

public class LoadedPanes {

    public static Pane registerMenu;
    public static Pane createAccount;
    public static Pane login;
    public static Pane leatherBoard;
    public static Scene shop;
    public static Scene collectionOfShop;
    public static Scene arena;

    {
        try {
            registerMenu = FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/registerMenu.fxml"));
            createAccount = FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/createAccount.fxml"));
            login = FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/login.fxml"));
            leatherBoard = FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/leatherBoard.fxml"));
            shop = new Scene(Utility.scale(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/shop.fxml"))));
            collectionOfShop = new Scene(Utility.scale(FXMLLoader.load(RegisterMenuController.class.getResource("../fxmls/collectionOfShop.fxml"))));
            arena = new Scene(Utility.scale(FXMLLoader.load(getClass().getResource("arena.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
