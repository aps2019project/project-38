package controller;

import controller.window.LoadWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

public class Main extends Application {
    public static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setFullScreen(true);
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        new LoadWindow().main();

        WindowChanger.instance.setNewScene(LoadedScenes.registerMenu);
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