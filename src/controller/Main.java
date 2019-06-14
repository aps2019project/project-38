package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.cards.CardFactory;
import view.fxmlControllers.ArenaController;
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

//        ArenaController.ac.init(null);
//        ArenaController.ac.put(4,3,"#GhoolKhafan2");
//        LoadedScenes.arena.setOnKeyTyped(event -> {
//            ArenaController.ac.attack(4,3,4,3);
//        });
        primaryStage.setScene(LoadedScenes.shop);

        ArenaController.ac.init(null);
        LoadedScenes.arena.setOnKeyTyped(event -> {
            ArenaController.ac.attack(0,0,4,4);
        });
        ArenaController.ac.put(0,0,"shahzadehtoorani");
//        ArenaController.ac.put(0,1,"shahzadehtoorani");
//        ArenaController.ac.put(0,2,"shahzadehtoorani");
//        ArenaController.ac.put(0,3,"shahzadehtoorani");
//        ArenaController.ac.put(0,4,"shahzadehtoorani");
//        ArenaController.ac.put(0,5,"shahzadehtoorani");
//        ArenaController.ac.put(0,6,"shahzadehtoorani");
//        ArenaController.ac.put(0,7,"shahzadehtoorani");
//        ArenaController.ac.put(0,8,"shahzadehtoorani");
//        ArenaController.ac.put(1,0,"shahzadehtoorani");
//        ArenaController.ac.put(1,1,"shahzadehtoorani");
//        ArenaController.ac.put(1,2,"shahzadehtoorani");
//        ArenaController.ac.put(1,3,"shahzadehtoorani");
//        ArenaController.ac.put(1,4,"shahzadehtoorani");
//        ArenaController.ac.put(1,5,"shahzadehtoorani");
//        ArenaController.ac.put(1,6,"shahzadehtoorani");
//        ArenaController.ac.put(1,7,"shahzadehtoorani");
//        ArenaController.ac.put(1,8,"shahzadehtoorani");
//        ArenaController.ac.put(2,0,"shahzadehtoorani");
//        ArenaController.ac.put(2,1,"shahzadehtoorani");
//        ArenaController.ac.put(2,2,"shahzadehtoorani");
//        ArenaController.ac.put(2,3,"shahzadehtoorani");
//        ArenaController.ac.put(2,4,"shahzadehtoorani");
        ArenaController.ac.put(2,5,"shahzadehtoorani");
//        ArenaController.ac.put(2,6,"shahzadehtoorani");
//        ArenaController.ac.put(2,7,"shahzadehtoorani");
//        ArenaController.ac.put(2,8,"shahzadehtoorani");
//        ArenaController.ac.put(3,0,"shahzadehtoorani");
//        ArenaController.ac.put(3,1,"shahzadehtoorani");
//        ArenaController.ac.put(3,2,"shahzadehtoorani");
//        ArenaController.ac.put(3,3,"shahzadehtoorani");
//        ArenaController.ac.put(3,4,"shahzadehtoorani");
//        ArenaController.ac.put(3,5,"shahzadehtoorani");
//        ArenaController.ac.put(3,6,"shahzadehtoorani");
//        ArenaController.ac.put(3,7,"shahzadehtoorani");
//        ArenaController.ac.put(3,8,"shahzadehtoorani");
//        ArenaController.ac.put(4,0,"shahzadehtoorani");
//        ArenaController.ac.put(4,1,"shahzadehtoorani");
//        ArenaController.ac.put(4,2,"shahzadehtoorani");
//        ArenaController.ac.put(4,3,"shahzadehtoorani");
//        ArenaController.ac.put(4,4,"shahzadehtoorani");
//        ArenaController.ac.put(4,5,"shahzadehtoorani");
//        ArenaController.ac.put(4,6,"shahzadehtoorani");
        ArenaController.ac.put(4,7,"shahzadehtoorani");
//        ArenaController.ac.put(4,8,"shahzadehtoorani");


        primaryStage.setScene(LoadedScenes.arena);

        primaryStage.show();
    }
}