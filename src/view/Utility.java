package view;

import controller.Main;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;

public class Utility {
    public static AnchorPane scale(AnchorPane pane){
        Main.mainStage.setFullScreen(true);
        double xScale = Screen.getPrimary().getVisualBounds().getWidth() / 480;
        double yScale = Screen.getPrimary().getVisualBounds().getHeight() / 270;
        Scale scale = new Scale(xScale, yScale, 0, 0);
        pane.getTransforms().add(scale);
        return pane;
    }
}
