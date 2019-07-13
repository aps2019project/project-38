package view.fxmlControllers;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.util.Random;

public class GameStartWaitingRoomController {
    public static GameStartWaitingRoomController gameStartWaitingRoomController;
    public ProgressBar progressBar;
    private boolean finished = false;

    public void enter() {
        Random random = new Random();
        double speed;
        Platform.runLater(() -> progressBar.setProgress(0));
        WindowChanger.instance.setMainParent(LoadedScenes.gameStartWaitingRoom);
        do {
            speed = random.nextInt(100) / 100000;
            final double newProgress = progressBar.getProgress() + speed < 1 ? progressBar.getProgress() + speed : 1;
            Platform.runLater(() -> progressBar.setProgress(newProgress));
        } while (finished && progressBar.getProgress() == 1);
    }

    public void exitToArena() {
        finished = true;
        ArenaController.ac.init();
        WindowChanger.instance.setMainParent(LoadedScenes.arena);
    }
}
