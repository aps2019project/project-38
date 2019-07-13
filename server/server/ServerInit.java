package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.Shop;
import model.cards.CardFactory;
import server.net.MatchMaker;
import server.net.ServerSession;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInit extends Application {
    public static Stage mainStage;

    public static void main(String[] args) throws IOException {
        CardFactory.main();
        Shop.getShop().loadShop();
        Deck.deckLevelBuilder();
        Account.loadAccounts();
        MatchMaker.makeMatchMakingThreads();
        new LoadedScenes();

        ServerSocket sc = new ServerSocket(8000); //todo : get port from config.txt
        new Thread(() -> {
            while (true) {
                Socket socket = null;
                System.out.println("Ghabl");
                try {
                    socket = sc.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Baad");
                new ServerSession(socket);
            }
        }).start();
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

        WindowChanger.instance.setMainParent(LoadedScenes.shop);
    }
}