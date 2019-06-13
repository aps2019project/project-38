package controller;

import controller.window.LoadWindow;
import controller.window.Window;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.fxmlControllers.ArenaController;
import view.fxmlControllers.CreateAccountController;
import view.fxmlControllers.RegisterMenuController;
import view.fxmls.LoadedPanes;
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
        new LoadedPanes();
//        primaryStage.setScene(RegisterMenuController.getScene());
//        primaryStage.setScene(LoadedPanes.shop);
        primaryStage.setScene(LoadedPanes.shop);
//        ArenaController.ac.init(null);
//        ArenaController.ac.put(4,4,"sp");
//        LoadedPanes.arena.setOnKeyTyped(event -> {
//            ArenaController.ac.attack(4,4,4,4);
//        });
//        primaryStage.setScene(LoadedPanes.arena);

        primaryStage.show();
    }
}