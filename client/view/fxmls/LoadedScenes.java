package view.fxmls;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import view.fxmlControllers.*;

import java.io.IOException;

import static view.Utility.scale;

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
    public static Pane auctions;
    public static Pane gameStartWaitingRoom;

    {
        try {
            registerMenu = scale(FXMLLoader.load(getClass().getResource("menus/registerMenu.fxml")));
            createAccount = scale(FXMLLoader.load(getClass().getResource("menus/createAccount.fxml")));
            login = scale(FXMLLoader.load(getClass().getResource("menus/login.fxml")));
            mainMenu = scale(FXMLLoader.load(getClass().getResource("menus/mainMenu.fxml")));
            chooseBattleKind = scale(FXMLLoader.load(getClass().getResource("chooseBattleKind.fxml")));
            arena = scale(FXMLLoader.load(getClass().getResource("arena.fxml")));
            fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("shop.fxml"));
            shop = scale(fxmlLoader.load());
            ShopController.shopController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("collectionOfShop.fxml"));
            collectionOfShop = scale(fxmlLoader.load());
            CollectionOfShopController.collectionOfShopController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("Collection.fxml"));
            collection = scale(fxmlLoader.load());
            CollectionController.collectionController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("ChoosingDeckCards.fxml"));
            choosingDeckCards = scale(fxmlLoader.load());
            ChoosingDeckCardsController.choosingDeckCardsController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("RemovingDeckCards.fxml"));
            removingDeckCards = scale(fxmlLoader.load());
            RemovingDeckCardsController.removingDeckCardsController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("Auctions.fxml"));
            auctions = scale(fxmlLoader.load());
            AuctionsController.auctionsController = fxmlLoader.getController();
            fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("GameStartWaitingRoom.fxml"));
            gameStartWaitingRoom = scale(fxmlLoader.load());
            GameStartWaitingRoomController.gameStartWaitingRoomController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanArena() {
        try {
            arena = scale(FXMLLoader.load(LoadedScenes.class.getResource("arena.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
