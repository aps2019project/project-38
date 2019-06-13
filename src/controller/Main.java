package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
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

        new LoadedImages();
        new LoadedScenes();
//        primaryStage.setScene(RegisterMenuController.getScene());
//        primaryStage.setScene(LoadedScenes.shop);
        primaryStage.setScene(LoadedScenes.shop);
//        ArenaController.ac.init(null);
//        ArenaController.ac.put(4,4,"sp");
//        LoadedScenes.arena.setOnKeyTyped(event -> {
//            ArenaController.ac.attack(4,4,4,4);
//        });
//        primaryStage.setScene(LoadedScenes.arena);

        primaryStage.show();
    }
}