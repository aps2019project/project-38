package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.Utility;
import view.fxmlControllers.RegisterMenuController;

import java.io.IOException;

public class LoadedScenes {

    public static Pane registerMenu;
    public static Pane createAccount;
    public static Pane login;
    public static Pane leatherBoard;
    public static Scene shop;
    public static Scene collectionOfShop;
    public static Scene arena;
    public static Scene cartTest;

    {
        try {
            registerMenu = FXMLLoader.load(getClass().getResource("registerMenu.fxml"));
            createAccount = FXMLLoader.load(getClass().getResource("createAccount.fxml"));
            login = FXMLLoader.load(getClass().getResource("login.fxml"));
            leatherBoard = FXMLLoader.load(getClass().getResource("leatherBoard.fxml"));
            shop = new Scene(Utility.scale(FXMLLoader.load(getClass().getResource("shop.fxml"))));
            collectionOfShop = new Scene(Utility.scale(FXMLLoader.load(getClass().getResource("collectionOfShop.fxml"))));
            arena = new Scene(Utility.scale(FXMLLoader.load(getClass().getResource("arena.fxml"))));
            cartTest = new Scene(FXMLLoader.load(getClass().getResource("warriorCart.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
