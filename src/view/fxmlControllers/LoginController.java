package view.fxmlControllers;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import view.fxmls.LoadedPanes;

import java.net.URL;
import java.util.ResourceBundle;

import static view.Utility.scale;

public class LoginController implements Initializable {
    private static Scene scene = null;
    public AnchorPane mainPane;

    public static Scene getScene() {
        if (scene == null) {
            scene = new Scene(LoadedPanes.login, 480, 270);
        }
        return scene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane = scale(mainPane);
    }
}
