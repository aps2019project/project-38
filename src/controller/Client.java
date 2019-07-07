package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.Game;
import model.Level;
import view.LoadWindows;
import view.WindowChanger;
import view.fxmlControllers.ArenaController;
import view.fxmls.LoadedScenes;

import java.io.IOException;

public class Client extends Application {
    public static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainStage = primaryStage;
        mainStage.setFullScreen(true);
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        new LoadWindows().main();

        WindowChanger.instance.setNewScene(LoadedScenes.registerMenu);
//        WindowChanger.instance.setNewScene(LoadedScenes.customCard);
//
//        {//arena
//            Account account = new Account("test", "test");
//            account.getCollection().setMainDeck(Deck.getAllDecks().get("level1"));
//            Game game = Level.getAvailableLevels().get("1").getLevelGame(account);
//            ArenaController.ac.init(game);
//            game.initialiseGameFields();//
//            WindowChanger.instance.setNewScene(LoadedScenes.arena);
//        }

        mainStage.show();
    }
}