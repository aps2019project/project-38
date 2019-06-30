package view.fxmlControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Account;
import model.Deck;
import model.Level;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.util.*;

public abstract class LoadingGamePreviewScenes {
    static HashMap<String, AnchorPane> starterScenes = new HashMap<>();
    static HashMap<String, GamePreviewStarterController> starterControllers = new HashMap<>();
    static HashMap<String, AnchorPane> scenesAsAnchorPane = new HashMap<>();
    static HashMap<String, GamePreviewController> sceneControllers = new HashMap<>();
    static ArrayList<String> selectedButtonsText = new ArrayList<>();

    public static void load() {
        loadBattleScene();
        loadSinglePlayerScene();
        loadMultiPlayerScene();
        loadStoryScene();
//        loadCustomScene();
        loadMoodScene();
        loadCollectingFlagScene();
        WindowChanger.instance.setNewScene(starterScenes.get("Battle"));
        starterControllers.get("Battle").run();
    }

    private static void loadBattleScene() {
        int imagesNumber = new Random().nextInt(6);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewStarter.fxml"));
            starterScenes.put("Battle", fxmlLoader.load());
            starterControllers.put("Battle", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreview.fxml"));
            scenesAsAnchorPane.put("Battle", fxmlLoader.load());
            sceneControllers.put("Battle", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane singlePlayerButton = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
            singlePlayerButton = fxmlLoader.load();
            ((GamePreviewButtonController)fxmlLoader.getController()).setFields("Single Player", "Battle", "Single Player");
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane multiPlayerButton = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
            multiPlayerButton = fxmlLoader.load();
            ((GamePreviewButtonController)fxmlLoader.getController()).setFields("Multi Player", "Battle", "Multi Player");
        } catch (IOException e) {
            e.printStackTrace();
        }
        starterControllers.get("Battle").setFields(imagesNumber, "Battle");
        sceneControllers.get("Battle").setFields("Battle", imagesNumber);
        sceneControllers.get("Battle").addButton(singlePlayerButton, multiPlayerButton);
        sceneControllers.get("Battle").setPreviewSceneName("Main Menu");
    }

    private static void loadSinglePlayerScene() {
        int imagesNumber = new Random().nextInt(6);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewStarter.fxml"));
            starterScenes.put("Single Player", fxmlLoader.load());
            starterControllers.put("Single Player", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreview.fxml"));
            scenesAsAnchorPane.put("Single Player", fxmlLoader.load());
            sceneControllers.put("Single Player", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane storyButton = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
            storyButton = fxmlLoader.load();
            ((GamePreviewButtonController)fxmlLoader.getController()).setFields("Story", "Single Player", "Story");
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane customButton = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
            customButton = fxmlLoader.load();
            ((GamePreviewButtonController)fxmlLoader.getController()).setFields("Custom", "Single Player", "Custom");
        } catch (IOException e) {
            e.printStackTrace();
        }
        starterControllers.get("Single Player").setFields(imagesNumber, "Single Player");
        sceneControllers.get("Single Player").setFields("Single Player", imagesNumber);
        sceneControllers.get("Single Player").addButton(storyButton, customButton);
    }

    private static void loadMultiPlayerScene() {
        int imagesNumber = new Random().nextInt(6);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewStarter.fxml"));
            starterScenes.put("Multi Player", fxmlLoader.load());
            starterControllers.put("Multi Player", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreview.fxml"));
            scenesAsAnchorPane.put("Multi Player", fxmlLoader.load());
            sceneControllers.put("Multi Player", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Account> entry : Account.getUsernameToAccountObject().entrySet()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader
                        (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
                sceneControllers.get("Multi Player").addButton(((AnchorPane)fxmlLoader.load()));
                ((GamePreviewButtonController)fxmlLoader.getController()).setFields(String.format("User Name: %s", entry.getKey()), "Multi Player", "Mood");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        starterControllers.get("Multi Player").setFields(imagesNumber, "Multi Player");
        sceneControllers.get("Multi Player").setFields("Multi Player", imagesNumber);
    }

    private static void loadStoryScene() {
        int imagesNumber = new Random().nextInt(6);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewStarter.fxml"));
            starterScenes.put("Story", fxmlLoader.load());
            starterControllers.put("Story", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreview.fxml"));
            scenesAsAnchorPane.put("Story", fxmlLoader.load());
            sceneControllers.put("Story", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Level> entry : Level.getAvailableLevels().entrySet()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader
                        (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
                sceneControllers.get("Story").addButton(((AnchorPane)fxmlLoader.load()));
                ((GamePreviewButtonController)fxmlLoader.getController()).setFields(String.format("Mode: %s\nHero: %s Prize: %d",
                        entry.getValue().getGameMode().getClass().getSimpleName(),
                        entry.getValue().getDeck().getHero().getName(), entry.getValue().getPrize()), "Story", "Game Window");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        starterControllers.get("Story").setFields(imagesNumber, "Story");
        sceneControllers.get("Story").setFields("Story", imagesNumber);
    }

    private static void loadCustomScene() {
        int imagesNumber = new Random().nextInt(6);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewStarter.fxml"));
            starterScenes.put("Custom", fxmlLoader.load());
            starterControllers.put("Custom", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreview.fxml"));
            scenesAsAnchorPane.put("Custom", fxmlLoader.load());
            sceneControllers.put("Custom", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Deck> entry : Account.getActiveAccount().getCollection().getAllDecks().entrySet()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader
                        (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
                sceneControllers.get("Custom").addButton(((AnchorPane)fxmlLoader.load()));
                ((GamePreviewButtonController)fxmlLoader.getController()).setFields(String.format("Deck: %s Hero: %s",
                        entry.getValue().getName(), entry.getValue().getHero().getName()), "Custom", "Mood");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        starterControllers.get("Custom").setFields(imagesNumber, "Custom");
        sceneControllers.get("Custom").setFields("Custom", imagesNumber);
    }

    private static void loadMoodScene() {
        int imagesNumber = new Random().nextInt(6);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewStarter.fxml"));
            starterScenes.put("Mood", fxmlLoader.load());
            starterControllers.put("Mood", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreview.fxml"));
            scenesAsAnchorPane.put("Mood", fxmlLoader.load());
            sceneControllers.put("Mood", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane killingEnemyHeroButton = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
            killingEnemyHeroButton = fxmlLoader.load();
            ((GamePreviewButtonController)fxmlLoader.getController()).setFields("Killing Enemy Hero", "Mood", "Game Window");
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane carryingFlagButton = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
            carryingFlagButton = fxmlLoader.load();
            ((GamePreviewButtonController)fxmlLoader.getController()).setFields("Carrying Flag", "Mood", "Game Window");
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane collectingFlagButton = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
            collectingFlagButton = fxmlLoader.load();
            ((GamePreviewButtonController)fxmlLoader.getController()).setFields("Collecting Flag", "Mood", "Collecting Flag");
        } catch (IOException e) {
            e.printStackTrace();
        }
        starterControllers.get("Mood").setFields(imagesNumber, "Mood");
        sceneControllers.get("Mood").setFields("Mood", imagesNumber);
        sceneControllers.get("Mood").addButton(killingEnemyHeroButton,carryingFlagButton, collectingFlagButton);
    }

    private static void loadCollectingFlagScene() {
        int imagesNumber = new Random().nextInt(6);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreviewStarter.fxml"));
            starterScenes.put("Collecting Flag", fxmlLoader.load());
            starterControllers.put("Collecting Flag", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader
                    (LoadedScenes.class.getResource("gamePreview.fxml"));
            scenesAsAnchorPane.put("Collecting Flag", fxmlLoader.load());
            sceneControllers.put("Collecting Flag", fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 5; i < 23; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader
                        (LoadedScenes.class.getResource("gamePreviewButton.fxml"));
                sceneControllers.get("Collecting Flag").addButton(((AnchorPane)fxmlLoader.load()));
                ((GamePreviewButtonController)fxmlLoader.getController()).setFields(String.valueOf(i), "Collecting Flag", "Game Window");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        starterControllers.get("Collecting Flag").setFields(imagesNumber, "Collecting Flag");
        sceneControllers.get("Collecting Flag").setFields("Collecting Flag", imagesNumber);
    }
}
