package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import view.Utility;
import view.fxmlControllers.ChoosingDeckCardsController;
import view.fxmlControllers.RemovingDeckCardsController;

import java.io.IOException;

import static view.Utility.scale;
import static view.Utility.twiceScale;

public class LoadedScenes {
    private static FXMLLoader fxmlLoader;
    public static Pane registerMenu;
    public static Pane createAccount;
    public static Pane login;
    public static Pane leatherBoard;
    public static Pane mainMenu;
    public static Pane chooseBattleKind;
    public static Pane shop;
    public static Pane collectionOfShop;
    public static Pane arena;
    public static Pane collection;
    public static Pane choosingDeckCards;
    public static Pane removingDeckCards;

    {
        try {
            registerMenu = scale(FXMLLoader.load(getClass().getResource("registerMenu.fxml")));
            createAccount = scale(FXMLLoader.load(getClass().getResource("createAccount.fxml")));
            login = scale(FXMLLoader.load(getClass().getResource("login.fxml")));
            mainMenu = scale(FXMLLoader.load(getClass().getResource("mainMenu.fxml")));
            chooseBattleKind = scale(FXMLLoader.load(getClass().getResource("chooseBattleKind.fxml")));
            shop = scale(FXMLLoader.load(getClass().getResource("shop.fxml")));
            arena = scale(FXMLLoader.load(getClass().getResource("arena.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanArena(){
        try {
            arena = scale(FXMLLoader.load(LoadedScenes.class.getResource("arena.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
