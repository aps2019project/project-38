package view.fxmlControllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Account;
import view.Utility;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public AnchorPane mainPane;
    public TextField username;
    public TextField password;
    public Pane alertWindow;
    public Label alert;
    private boolean shouldClose = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("collectionOfShop.fxml"));//todo don't change this
            try {
                LoadedScenes.collectionOfShop = fxmlLoader.load();
                CollectionOfShopController.collectionOfShopController = fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void back() {
        WindowChanger.instance.setNewScene(RegisterMenuController.getScene());
        username.clear();
        password.clear();
    }

    public void ok() {
        alertWindow.toBack();
        if (shouldClose) {
            WindowChanger.instance.setNewScene(LoadedScenes.mainMenu);
            shouldClose = false;
            username.clear();
            password.clear();
        }
    }
}
