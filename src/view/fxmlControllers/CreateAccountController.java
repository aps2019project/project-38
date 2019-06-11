package view.fxmlControllers;

import controller.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static view.Utility.*;

public class CreateAccountController implements Initializable {

    public AnchorPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane=scale(mainPane);
    }

    public TextField username;
    public TextField password;
    public TextField again;
}