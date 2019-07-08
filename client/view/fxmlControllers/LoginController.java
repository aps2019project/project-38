package view.fxmlControllers;

import client.Messages;
import client.net.ClientConnector;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

public class LoginController {
    public AnchorPane mainPane;
    public TextField username;
    public TextField password;
    public Pane alertWindow;
    public Label alert;
    private boolean shouldClose = false;


    public void login() {
        String stringUsername = username.getText();
        String stringPassword = password.getText();

        ClientConnector.printer.print(stringUsername);
        ClientConnector.printer.print(stringPassword);
        ClientConnector.printer.print(Messages.login);
        String result = ClientConnector.scanner.next();

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
        WindowChanger.instance.setMainParent(LoadedScenes.registerMenu);
        username.clear();
        password.clear();
    }

    public void ok() {
        alertWindow.toBack();
        if (shouldClose) {
            WindowChanger.instance.setMainParent(LoadedScenes.mainMenu);
            shouldClose = false;
            username.clear();
            password.clear();
        }
    }
}
