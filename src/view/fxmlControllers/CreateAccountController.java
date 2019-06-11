package view.fxmlControllers;

import controller.Main;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {

    public AnchorPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double xScale = Screen.getPrimary().getVisualBounds().getWidth() / 480;
        double yScale = Screen.getPrimary().getVisualBounds().getHeight() / 270;

        Scale scale = new Scale(xScale, yScale, 0, 0);

        mainPane.getTransforms().add(scale);
    }

    public TextField username;
    public TextField password;
    public TextField again;
}