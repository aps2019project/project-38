package controller;

import controller.window.LoadWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.cards.CardFactory;
import view.fxmlControllers.ArenaController;
import view.fxmlControllers.LoadingGamePreviewScenes;
import view.fxmlControllers.ShopController;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;
import view.visualentities.VisualMinion;

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
        CardFactory.main();
        new LoadedImages();
        new LoadedScenes();
        new LoadWindow().main();

//        ArenaController.ac.init(null);
//        ArenaController.ac.put(4,3,"#GhoolKhafan2");
//        LoadedScenes.arena.setOnKeyTyped(event -> {
//            ArenaController.ac.attack(4,3,4,3);
//        });
        LoadingGamePreviewScenes.load();


        primaryStage.show();
    }
}