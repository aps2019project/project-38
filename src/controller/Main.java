package controller;

import controller.window.LoadWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.Game;
import model.Level;
import model.cards.CardFactory;
import model.exceptions.NotEnoughConditions;
import view.fxmlControllers.ArenaController;
import view.fxmlControllers.LoadingGamePreviewScenes;
import view.fxmlControllers.WindowChanger;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public class Main extends Application {
    public static Stage mainStage;

    public static void main(String[] args) {
//        new LoadWindow().openWindow();
//        while (true) {
//            if (!Window.runLastOpenWindow()) {
//                break;
//            }
//        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setFullScreen(true);
        mainStage.setFullScreenExitHint("");
        mainStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        new LoadWindow().main();

//        LoadingGamePreviewScenes.load();

        {//arena
            Account account = new Account("test", "test");
            Deck.deckLevelBuilder();
            account.getCollection().setMainDeck(Deck.getAllDecks().get("level1"));
            Game game = Level.getAvailableLevels().get("1").getLevelGame(account);
            ArenaController.ac.init(game);
            game.initialiseGameFields();
            LoadedScenes.arena.setOnKeyTyped(event -> {
                ArenaController.ac.attack(0, 0, 4, 4);
            });

            game.getActivePlayer().mana = 20;
            try {
                game.useCard(1, game.getBoard().getCell(2, 6));
            } catch (NotEnoughConditions notEnoughConditions) {
                System.out.println(notEnoughConditions.getMessage());
            }
            try {
                game.useCard(0, game.getBoard().getCell(4, 2));
            } catch (NotEnoughConditions notEnoughConditions) {
                System.out.println(notEnoughConditions.getMessage());
            }
            LoadedScenes.arena.setOnKeyTyped(event -> {
            });
            WindowChanger.instance.setNewScene(LoadedScenes.arena);
        }

        primaryStage.show();
    }
}