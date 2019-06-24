package view.fxmlControllers;

import controller.Main;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import view.fxmls.LoadedScenes;

public class GamePreviewButtonController {
    public ImageView buttonImageView;
    public Text buttonText;
    private String fatherSceneName, nextSceneName;

    public void doClickEvents(MouseEvent mouseEvent) {
        LoadingGamePreviewScenes.sceneControllers.get(nextSceneName).setPreviewSceneName(fatherSceneName);
        Main.mainStage.setScene(LoadingGamePreviewScenes.starterScenes.get(nextSceneName));
        Main.mainStage.setFullScreen(true);
        LoadingGamePreviewScenes.starterControllers.get(nextSceneName).run();
    }

    public void shineButton(MouseEvent mouseEvent) {
        buttonImageView.setEffect(new Glow(0.5));
    }

    public void resetButton(MouseEvent mouseEvent) {
        buttonImageView.setEffect(null);
    }

    public void setFields(String text,String fatherSceneName, String nextSceneName) {
        buttonText.setText(text);
        this.fatherSceneName = fatherSceneName;
        this.nextSceneName = nextSceneName;
    }
}
