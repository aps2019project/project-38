package client.controller;

import client.net.ClientSession;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.Cell;
import model.cards.Warrior;
import view.Loader;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientInit extends Application {
    public static Stage mainStage;

    public static void main(String[] args) {
        ClientSession.connect();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Cell c =new Cell(null,2,2);
        Warrior w = new Warrior(1,"a",2,2,2,2);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(w);
        System.out.println(bos.size());

        mainStage = primaryStage;
        mainStage.setFullScreen(true);
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        Loader.loadAll();

        WindowChanger.instance.setMainParent(LoadedScenes.registerMenu);

//        {//arena
//            Account account = new Account("test", "test");
//            account.getCollection().setMainDeck(Deck.getAllDecks().get("level1"));
//            Game game = Level.getAvailableLevels().get("1").getLevelGame(account);
//            ArenaController.ac.init(game);
//            game.initialiseGameFields();//
//            WindowChanger.instance.setMainParent(LoadedScenes.arena);
//        }

        mainStage.show();
    }
}