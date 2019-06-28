package controller;

import com.sun.webkit.graphics.WCImage;
import controller.window.LoadWindow;
import controller.window.Window;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.Game;
import model.Level;
import view.fxmlControllers.ArenaController;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

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

        WindowChanger.instance.setNewScene(LoadedScenes.registerMenu);

//        LoadingGamePreviewScenes.load();

        {//arena
            Account account = new Account("test", "test");
            Deck.deckLevelBuilder();
            account.getCollection().setMainDeck(Deck.getAllDecks().get("level1"));
            Game game = Level.getAvailableLevels().get("1").getLevelGame(account);
            ArenaController.ac.init(game);
            game.initialiseGameFields();

            WindowChanger.instance.setNewScene(LoadedScenes.arena);
        }

        primaryStage.show();
    }
}