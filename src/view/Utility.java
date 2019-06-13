package view;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;

public class Utility {
    public static Pane scale(Pane pane){
        double xScale = Screen.getPrimary().getVisualBounds().getWidth() / (pane.getPrefWidth());
        double yScale = Screen.getPrimary().getVisualBounds().getHeight() / (pane.getPrefHeight() - 20);
        Scale scale = new Scale(xScale, yScale, 0, 0);
        pane.getTransforms().add(scale);
        return pane;
    }
}
