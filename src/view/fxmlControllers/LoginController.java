package view.fxmlControllers;

import controller.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Account;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.ResourceBundle;

import static view.Utility.scale;

public class LoginController implements Initializable {
    public AnchorPane mainPane;
    public TextField username;
    public TextField password;
    public Pane alertWindow;
    public Label alert;
    private boolean shouldClose = false;

    public static Scene getScene() {
        return LoadedScenes.login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane = scale(mainPane);
    }

    public void login() {
        String stringUsername = username.getText();
        String stringPassword = password.getText();
        String result = Account.login(stringUsername, stringPassword);
        if (result.equals("There is no account with this name")) {
            alertWindow.toFront();
            alert.setText("There is no account with this name");
        }
        if (result.equals("Your password is incorrect")) {
            alertWindow.toFront();
            alert.setText("Your password is incorrect");
        }
        if (result.equals("You logged in successfully")) {
            alertWindow.toFront();
            alert.setText("You logged in successfully");
            shouldClose = true;
        }
    }

    public void back() {
        Main.mainStage.setScene(RegisterMenuController.getScene());
        Main.mainStage.setFullScreen(true);
    }

    public void ok() {
        alertWindow.toBack();
        if (shouldClose) {
            Main.mainStage.setScene(LoadedScenes.mainMenu);
            Main.mainStage.setFullScreen(true);
            shouldClose = false;
            username.clear();
            password.clear();
        }
    }
}
