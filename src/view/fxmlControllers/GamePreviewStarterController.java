package view.fxmlControllers;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import view.images.LoadedImages;

public class GamePreviewStarterController {
    public AnchorPane faceAnchorPane, shadowAnchorPane;
    public ImageView face = new ImageView(), shadow = new ImageView();
    public AnchorPane gamePreviewAnchorPane;
    public GamePreviewController gamePreviewController;
    public double startYPropertyOfGamePreviewScene;

    public void run() {
        gamePreviewAnchorPane.setLayoutY(startYPropertyOfGamePreviewScene);
        shadow.setOpacity(1);
        gamePreviewController.titleText.setOpacity(0);
        gamePreviewController.titleBackgroundImage.setOpacity(0);
        gamePreviewController.backButton.setOpacity(0);
        new Thread(() -> {
            while (shadow.getOpacity() != 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double newOpacity = shadow.getOpacity() - 0.0003 > 0 ? shadow.getOpacity() - 0.0003 : 0;
                Platform.runLater(() -> shadow.setOpacity(newOpacity));
            }
            while (gamePreviewAnchorPane.getLayoutY() != 0) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double newYProperty = gamePreviewAnchorPane.getLayoutY() - 1 > 0 ? gamePreviewAnchorPane.getLayoutY() - 1 : 0;
                Platform.runLater(() -> gamePreviewAnchorPane.setLayoutY(newYProperty));
            }
            gamePreviewController.run();
        }).start();
    }

    public void setFields(int faceAndShadowNumber, String gamePreviewSceneName) {
        face.setImage(LoadedImages.notStaticBeforeGameImages.get("face").get(faceAndShadowNumber));
        shadow.setImage(LoadedImages.notStaticBeforeGameImages.get("shadow").get(faceAndShadowNumber));
        faceAnchorPane.getChildren().add(face);
        shadowAnchorPane.getChildren().add(shadow);
        scaleImageViewInsideAnchorPane(faceAnchorPane, face);
        scaleImageViewInsideAnchorPane(shadowAnchorPane, shadow);
        gamePreviewAnchorPane.getChildren().addAll(LoadingGamePreviewScenes.scenesAsAnchorPane.get(gamePreviewSceneName));
        gamePreviewController = LoadingGamePreviewScenes.sceneControllers.get(gamePreviewSceneName);
        startYPropertyOfGamePreviewScene = Screen.getPrimary().getVisualBounds().getHeight();
    }

    private void scaleImageViewInsideAnchorPane(AnchorPane anchorPane, ImageView imageView) {
        imageView.setFitWidth(1);
        imageView.setFitHeight(1);
        double xScale = anchorPane.getPrefWidth() / imageView.getFitWidth();
        double yScale = anchorPane.getPrefHeight() / imageView.getFitHeight();
        Scale scale = new Scale(xScale, yScale, 0, 0);
        imageView.getTransforms().add(scale);
    }
}
