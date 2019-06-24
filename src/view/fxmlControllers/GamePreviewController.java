package view.fxmlControllers;

import controller.Main;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;

public class GamePreviewController {

    public ImageView backButton;
    public Text titleText;
    public VBox leftVBox;
    public VBox rightVBox;
    public AnchorPane backgroundAnchorPane;
    private String previewSceneName;

    public void back(MouseEvent mouseEvent) {
        if (LoadingGamePreviewScenes.selectedButtonsText.size() > 0)
            LoadingGamePreviewScenes.selectedButtonsText.remove(LoadingGamePreviewScenes.selectedButtonsText.size() - 1);
        if (previewSceneName.equals("Main Menu")) {
            Main.mainStage.setScene(LoadedScenes.mainMenu);
            Main.mainStage.setFullScreen(true);
        }
        else {
            Main.mainStage.setScene(LoadingGamePreviewScenes.starterScenes.get(previewSceneName));
            Main.mainStage.setFullScreen(true);
            LoadingGamePreviewScenes.starterControllers.get(previewSceneName).run();
        }
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow(0.5));
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    public void setFields(String title, int backgroundNumber) {
        titleText.setText(title);
        ImageView backgroundImageView =
                new ImageView(LoadedImages.notStaticBeforeGameImages.get("background").get(backgroundNumber));
        backgroundAnchorPane.getChildren().add(backgroundImageView);
        backgroundImageView.setFitWidth(1);
        backgroundImageView.setFitHeight(1);
        double xScale = backgroundAnchorPane.getPrefWidth() / backgroundImageView.getFitWidth();
        double yScale = backgroundAnchorPane.getPrefHeight() / backgroundImageView.getFitHeight();
        Scale scale = new Scale(xScale, yScale, 0, 0);
        backgroundImageView.getTransforms().add(scale);
    }

    public void addButton(AnchorPane... buttons) {
        for (AnchorPane button : buttons) {
            if (leftVBox.getChildren().size() <= rightVBox.getChildren().size()) leftVBox.getChildren().add(button);
            else rightVBox.getChildren().add(button);
        }
    }

    public void setPreviewSceneName(String previewSceneName) {
        this.previewSceneName = previewSceneName;
    }
}
