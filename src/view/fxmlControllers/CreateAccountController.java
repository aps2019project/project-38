package view.fxmlControllers;

import controller.Main;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Account;
import view.fxmls.LoadedPanes;

import java.net.URL;
import java.util.ResourceBundle;

import static view.Utility.scale;

public class CreateAccountController implements Initializable {

    public static Scene scene;
    public AnchorPane mainPane;
    public TextField username;
    public TextField password;
    public TextField again;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane = scale(mainPane);
    }

    public static Scene getScene() {
        if (scene == null) {
            scene = new Scene(LoadedPanes.createAccount, 480, 270);
        }
        return scene;
    }

    public void register() {
        String userNameString = username.getText();
        String passwordString = password.getText();
        String againPasswordString = again.getText();
        String result = Account.createAccount(userNameString, passwordString, againPasswordString);
        if (result.equals("There's an account with this name")) {
            //todo
        }
        if (result.equals("Your passwords aren't same")) {
            //todo
        }
        if (result.equals("Account created successfully")) {
            //todo
        }
    }

    public void cancel() {
        Main.mainStage.setScene(RegisterMenuController.getScene());
        Main.mainStage.setFullScreen(true);
    }
}