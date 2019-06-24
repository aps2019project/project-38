package view.fxmlControllers;

import controller.Main;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import view.images.LoadedImages;

public class GamePreviewStarterController {
    public AnchorPane faceAnchorPane, shadowAnchorPane;
    public ImageView face = new ImageView(), shadow = new ImageView();
    private Scene gamePreviewScene;

    public void run() {
        new Thread(() -> {
            while (shadow.getOpacity() != 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double newOpacity = shadow.getOpacity() - 0.0003;
                shadow.setOpacity(newOpacity > 0 ? newOpacity : 0);
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                Main.mainStage.setScene(gamePreviewScene);
                Main.mainStage.setFullScreen(true);
                shadow.setOpacity(1);
            });
        }).start();
    }

    public void setFields(int faceAndShadowNumber, Scene gamePreviewScene) {
        face.setImage(LoadedImages.notStaticBeforeGameImages.get("face").get(faceAndShadowNumber));
        shadow.setImage(LoadedImages.notStaticBeforeGameImages.get("shadow").get(faceAndShadowNumber));
        faceAnchorPane.getChildren().add(face);
        shadowAnchorPane.getChildren().add(shadow);
        scaleImageViewInsideAnchorPane(faceAnchorPane, face);
        scaleImageViewInsideAnchorPane(shadowAnchorPane, shadow);
        this.gamePreviewScene = gamePreviewScene;
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
