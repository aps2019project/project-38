package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.net.ServerSession;
import view.Loader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInit extends Application {
    public static Stage mainStage;

    public static void main(String[] args) throws IOException {
        ServerSocket sc = new ServerSocket(8000); //todo : get port from config.txt
        new Thread(() -> {
            while (true) {
                Socket socket = null;
                try {
                    socket = sc.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new ServerSession(socket);
            }
        }).start();
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        Loader.loadAll();

//        mainStage = primaryStage;
//        mainStage.setFullScreen(true);
//        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
//        mainStage.setOnCloseRequest(event -> {
//            Shop.getShop().saveShop();
//            Platform.exit();
//            System.exit(0);
//        });
//
//        WindowChanger.instance.setMainParent(LoadedScenes.shop);
//
//        mainStage.show();
    }
}