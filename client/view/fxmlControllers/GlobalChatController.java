package view.fxmlControllers;

import client.net.ClientSession;
import client.net.Encoder;
import client.net.Message;
import com.google.gson.Gson;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.Account;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GlobalChatController implements Initializable {
    public TextField message;
    public VBox messagesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Encoder.sendMessage(Message.showPreviousMessages);
        Gson gson = new Gson();
        ArrayList<Pair<String, String>> messages = null;
        try {
            messages = (ArrayList<Pair<String, String>>) gson.fromJson(ClientSession.dis.readUTF(), ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Pair<String, String> message : messages) {
            Label label = new Label();
            if (message.getKey().equals(Account.activeAccount.username)) {
                label.setText(message.getValue() + " :" + message.getKey());
                label.setAlignment(Pos.CENTER_RIGHT);
            } else {
                label.setText(message.getKey() + ": " + message.getValue());
                label.setAlignment(Pos.CENTER_LEFT);
            }
            label.resize(330, 70);
            messagesList.getChildren().add(label);
        }
    }

    public void send() {
        Encoder.sendMessage(Message.sendMessage);
        Encoder.sendString(Account.activeAccount.username);
        Encoder.sendString(message.getText());
    }

    public void back() {
        message.clear();
        WindowChanger.instance.setMainParent(LoadedScenes.mainMenu);
    }
}
