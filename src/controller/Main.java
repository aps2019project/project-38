package controller;

import controller.window.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;
import view.fxmlControllers.MainMenuController;
import view.fxmlControllers.RegisterMenuController;
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
        
        new LoadedImages();
        new LoadedScenes();
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
//        primaryStage.setScene(LoadedScenes.mainMenu);
        primaryStage.setScene(LoadedScenes.registerMenu);
//        primaryStage.setScene(LoadedPanes.shop);
        primaryStage.show();
    }
}