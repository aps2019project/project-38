package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import view.Utility;

import java.io.IOException;

import static view.Utility.scale;

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
    public static Pane createDeck;

    {
        try {
            registerMenu = scale(FXMLLoader.load(getClass().getResource("registerMenu.fxml")));
            createAccount = scale(FXMLLoader.load(getClass().getResource("createAccount.fxml")));
            login = scale(FXMLLoader.load(getClass().getResource("login.fxml")));
            mainMenu = scale(FXMLLoader.load(getClass().getResource("mainMenu.fxml")));
            chooseBattleKind = scale(FXMLLoader.load(getClass().getResource("chooseBattleKind.fxml")));
            shop = scale(FXMLLoader.load(getClass().getResource("shop.fxml")));
            arena = scale(FXMLLoader.load(getClass().getResource("arena.fxml")));
            createDeck = scale(FXMLLoader.load(getClass().getResource("createDeck.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
