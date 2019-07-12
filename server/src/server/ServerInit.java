package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.Account;
import server.net.ServerSession;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInit extends Application {
    public static Stage mainStage;

    public static void main(String[] args) throws IOException {
        Account.loadAccounts();

        ServerSocket sc = new ServerSocket(8000); //todo : get port from config.txt
        launch(args);
        while (true) {
            Socket socket = sc.accept();
            new ServerSession(socket);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setFullScreen(true);
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainStage.setOnCloseRequest(event -> {

            Platform.exit();
            System.exit(0);
        });
    }
}