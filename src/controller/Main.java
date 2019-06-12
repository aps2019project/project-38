package controller;

import controller.window.LoadWindow;
import controller.window.Window;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        new LoadedImages();
        new LoadedPanes();
        primaryStage.setScene(LoadedPanes.shop);
        primaryStage.show();
    }
}
