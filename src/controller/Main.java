package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.cards.CardFactory;
import view.fxmlControllers.ArenaController;
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
        CardFactory.main();
        new LoadedImages();
        new LoadedScenes();

//        primaryStage.setScene(LoadedScenes.shop);
//
        ArenaController.ac.init(null);
        LoadedScenes.arena.setOnKeyTyped(event -> {
            ArenaController.ac.attack(0,0,4,4);
        });
        ArenaController.ac.put(0,0,"Siavash");
        ArenaController.ac.put(2,5,"Siavash");
        ArenaController.ac.put(4,7,"Siavash");
        primaryStage.setScene(LoadedScenes.arena);

        primaryStage.show();
    }
}