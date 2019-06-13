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

public class LoginController implements Initializable {
    private static Scene scene = null;
    public AnchorPane mainPane;
    public TextField username;
    public TextField password;

    public static Scene getScene() {
        if (scene == null) {
            scene = new Scene(LoadedPanes.login, 480, 270);
        }
        return scene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane = (AnchorPane) scale(mainPane);
    }

    public void login() {
        String stringUsername = username.getText();
        String stringPassword = password.getText();
        String result = Account.login(stringUsername, stringPassword);
        if(result.equals("There is no account with this name")){
            //todo
        }
        if(result.equals("Your password is incorrect")){
            //todo
        }
        if(result.equals("You logged in successfully")){
            //todo
        }
    }

    public void back() {
        Main.mainStage.setScene(RegisterMenuController.getScene());
        Main.mainStage.setFullScreen(true);
    }
}
