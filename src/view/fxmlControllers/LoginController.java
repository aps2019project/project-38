package view.fxmlControllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static view.Utility.scale;

public class LoginController implements Initializable {
    public AnchorPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane = scale(mainPane);
    }
}
