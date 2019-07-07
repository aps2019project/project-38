package view;

//import client.controller.ClientInit;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Popup;
import javafx.stage.Screen;

import java.util.Timer;
import java.util.TimerTask;

public class Utility {
    public static Pane scale(Pane pane) {
        double xScale = Screen.getPrimary().getVisualBounds().getWidth() / pane.getPrefWidth();
        double yScale = Screen.getPrimary().getVisualBounds().getHeight() / pane.getPrefHeight();
        Scale scale = new Scale(xScale, yScale, 0, 0);
        pane.getTransforms().add(scale);
        return pane;
    }

    public static TabPane tScale(TabPane pane){//could overload but then i should have casted all loads in loadedScenes.
        double xScale = Screen.getPrimary().getVisualBounds().getWidth() / pane.getPrefWidth();
        double yScale = Screen.getPrimary().getVisualBounds().getHeight() / pane.getPrefHeight();
        Scale scale = new Scale(xScale, yScale, 0, 0);
        pane.getTransforms().add(scale);
        return pane;
    }

    public static Pane twiceScale(Pane pane) {
        Scale scale = new Scale(2, 2, 0, 0);
        pane.getTransforms().add(scale);
        return pane;
    }

    public static AnchorPane scaleCard(AnchorPane pane) {
        double scaleDouble = 260f / pane.getPrefWidth();
        Scale scale = new Scale(scaleDouble, scaleDouble, 0, 0);
        pane.getTransforms().add(scale);
        return pane;
    }

    public static void showMessage(String message) {
//        Popup popup = new Popup();
//        Label label = new Label(message);
//        label.setBackground(new Background(new BackgroundFill(Color.gray(.5, .5), new CornerRadii(10), new Insets(-5, -10, -5, -10))));
//        label.setTextAlignment(TextAlignment.CENTER);
//        label.setFont(new Font(30));
//        label.setTextFill(Color.WHITE);
//        popup.getContent().add(label);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(popup::hide);
//            }
//        }, 2000);
//        popup.show(ClientInit.mainStage);
    }
}
