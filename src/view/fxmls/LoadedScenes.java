package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import view.fxmlControllers.RegisterMenuController;

import java.io.IOException;

import static view.Utility.*;

public class LoadedScenes {

    public static Pane registerMenu;
    public static Pane createAccount;
    public static Pane login;
    public static Pane leatherBoard;
    public static Pane mainMenu;
    public static Pane chooseBattleKind;
    public static Pane shop;
    public static Pane collectionOfShop;
    public static Pane arena;

    {
        try {
            registerMenu = twiceScale(FXMLLoader.load(getClass().getResource("registerMenu.fxml")));
            createAccount = twiceScale(FXMLLoader.load(getClass().getResource("createAccount.fxml")));
            login = twiceScale(FXMLLoader.load(getClass().getResource("login.fxml")));
            mainMenu = twiceScale(FXMLLoader.load(getClass().getResource("mainMenu.fxml")));
            chooseBattleKind = twiceScale(FXMLLoader.load(getClass().getResource("chooseBattleKind.fxml")));
            shop = FXMLLoader.load(getClass().getResource("shop.fxml"));
            collectionOfShop = FXMLLoader.load(getClass().getResource("collectionOfShop.fxml"));
            arena = twiceScale(FXMLLoader.load(getClass().getResource("arena.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
