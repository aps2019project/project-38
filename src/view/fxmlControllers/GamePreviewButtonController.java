package view.fxmlControllers;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Account;
import model.Deck;
import model.Game;
import model.Level;
import model.gamemodes.CarryingFlag;
import model.gamemodes.CollectingFlag;
import model.gamemodes.GameMode;
import model.gamemodes.KillingEnemyHero;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamePreviewButtonController {
    public ImageView buttonImageView;
    public Label buttonText;
    private String fatherSceneName, nextSceneName;

    public void doClickEvents(MouseEvent mouseEvent) {


        LoadingGamePreviewScenes.selectedButtonsText.add(buttonText.getText());
        if (nextSceneName.equals("Game Window")) {
            Game game;
            if (LoadingGamePreviewScenes.selectedButtonsText.get(0).equals("Single Player")) {

                if (LoadingGamePreviewScenes.selectedButtonsText.get(1).equals("Story")) {
                    System.out.println(LoadingGamePreviewScenes.selectedButtonsText.get(2));
                    Matcher matcher = Pattern.compile("Mode: .+\nHero: .+ Prize: (?<prize>\\d+)")
                            .matcher(LoadingGamePreviewScenes.selectedButtonsText.get(2));
                    System.out.println(matcher.matches());
                    int level = Integer.parseInt(matcher.group("prize")) / 500;
                    game = Level.getAvailableLevels().get(String.valueOf(level)).getLevelGame(Account.getActiveAccount());
                } else {
                    Matcher matcher = Pattern.compile("Deck: (?<deckName>.+) Hero: .+")
                            .matcher(LoadingGamePreviewScenes.selectedButtonsText.get(2));
                    matcher.matches();
                    Deck deck = Account.getActiveAccount().getCollection().getAllDecks().get(matcher.group("deckName"));
                    game = new Game(getMoodForStartingGame(2), Account.getActiveAccount(), deck);
                }
            } else {
                Account account = Account.getUsernameToAccountObject().get(LoadingGamePreviewScenes.selectedButtonsText.get(1));
                game = new Game(getMoodForStartingGame(2), Account.getActiveAccount(), account);
            }

            Platform.runLater(() -> {
                ArenaController.ac.init(game);
                game.initialiseGameFields();
                WindowChanger.instance.setMainParent(LoadedScenes.arena);
            });
        } else {
            LoadingGamePreviewScenes.sceneControllers.get(nextSceneName).setPreviewSceneName(fatherSceneName);
            WindowChanger.instance.setMainParent(LoadingGamePreviewScenes.starterScenes.get(nextSceneName));
            LoadingGamePreviewScenes.starterControllers.get(nextSceneName).run();
        }
    }

    private GameMode getMoodForStartingGame(int index) {
        if (LoadingGamePreviewScenes.selectedButtonsText.get(index).equals("Killing Enemy Hero")) {
            return new KillingEnemyHero();
        } else if (LoadingGamePreviewScenes.selectedButtonsText.get(index).equals("Carrying Flag")) {
            return new CarryingFlag();
        } else {
            return new CollectingFlag(Integer.parseInt(LoadingGamePreviewScenes.selectedButtonsText.get(index)));
        }
    }

    public void shineButton(MouseEvent mouseEvent) {
        buttonImageView.setEffect(new Glow(0.5));
    }

    public void resetButton(MouseEvent mouseEvent) {
        buttonImageView.setEffect(null);
    }


    public void setFields(String text, String fatherSceneName, String nextSceneName) {
        buttonText.setText(text);
        this.fatherSceneName = fatherSceneName;
        this.nextSceneName = nextSceneName;
    }
}
