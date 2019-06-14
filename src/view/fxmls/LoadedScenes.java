package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import view.fxmlControllers.RegisterMenuController;

import java.io.IOException;

import static view.Utility.*;

public class LoadedScenes {

    public static Scene registerMenu;
    public static Scene createAccount;
    public static Scene login;
    public static Scene leatherBoard;
    public static Scene mainMenu;
    public static Scene chooseBattleKind;
    public static Scene shop;
    public static Scene collectionOfShop;
    public static Scene arena;

    {
        try {
            registerMenu = new Scene(scale(FXMLLoader.load(getClass().getResource("registerMenu.fxml"))));
            createAccount = new Scene(scale(FXMLLoader.load(getClass().getResource("createAccount.fxml"))));
            login = new Scene(scale(FXMLLoader.load(getClass().getResource("login.fxml"))));
            mainMenu = new Scene(scale(FXMLLoader.load(getClass().getResource("mainMenu.fxml"))));
            chooseBattleKind = new Scene(scale(FXMLLoader.load(getClass().getResource("chooseBattleKind.fxml"))));
            shop = new Scene(scale(FXMLLoader.load(getClass().getResource("shop.fxml"))));
            collectionOfShop = new Scene(scale(FXMLLoader.load(getClass().getResource("collectionOfShop.fxml"))));
            arena = new Scene(scale(FXMLLoader.load(getClass().getResource("arena.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
