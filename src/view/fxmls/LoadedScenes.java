package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import view.Utility;

import java.io.IOException;

import static view.Utility.scale;
import static view.Utility.twiceScale;

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
            shop = scale(FXMLLoader.load(getClass().getResource("shop.fxml")));
            arena = FXMLLoader.load(getClass().getResource("arena.fxml"));
            arena.getTransforms().add(new Scale(960d / 1248, 540d / 682, 0, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
