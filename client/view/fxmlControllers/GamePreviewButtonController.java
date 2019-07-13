package view.fxmlControllers;

import client.net.Encoder;
import client.net.Message;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.WindowChanger;

public class GamePreviewButtonController {
    public ImageView buttonImageView;
    public Label buttonText;
    private String fatherSceneName, nextSceneName;

    public void doClickEvents(MouseEvent mouseEvent) {
        LoadingGamePreviewScenes.selectedButtonsText.add(buttonText.getText());
        if (nextSceneName.equals("Game Window")) {
            Encoder.sendMessage(Message.StartGame);
            Encoder.sendObjectJ(LoadingGamePreviewScenes.selectedButtonsText);
            GameStartWaitingRoomController.gameStartWaitingRoomController.enter();
        } else {
            LoadingGamePreviewScenes.sceneControllers.get(nextSceneName).setPreviewSceneName(fatherSceneName);
            WindowChanger.instance.setMainParent(LoadingGamePreviewScenes.starterScenes.get(nextSceneName));
            LoadingGamePreviewScenes.starterControllers.get(nextSceneName).run();
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
