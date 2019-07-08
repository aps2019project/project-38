package view.fxmlControllers;

import client.net.Message;
import client.net.Encoder;
import client.net.ClientSession;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;

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

        Encoder.sendMessage(Message.login);
        Encoder.sendString(stringUsername);
        Encoder.sendString(stringPassword);
        String result = null;
        try {
            result = ClientSession.dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
