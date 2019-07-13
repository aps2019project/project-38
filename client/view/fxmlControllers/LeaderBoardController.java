package view.fxmlControllers;

import client.net.Encoder;
import client.net.Message;
import com.google.gson.Gson;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.SepiaTone;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable {
    public static LeaderBoardController lbc;
    public AnchorPane mainPane;
    public VBox rate;
    public Label index;
    public VBox username;
    public VBox numOfWin;

    public static Pane getScene() {
        return LoadedScenes.leatherBoard;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbc = this;
        Encoder.sendMessage(Message.updateRanking);

        for (int i = 1; i < rate.getChildren().size(); i++) {
            rate.getChildren().remove(rate.getChildren().get(i));
            username.getChildren().remove(username.getChildren().get(i));
            numOfWin.getChildren().remove(numOfWin.getChildren().get(i));
        }


    }


    public void back() {
        WindowChanger.instance.setMainParent(LoadedScenes.registerMenu);
    }


    public void handleUpdateRanking(String result) {
        ArrayList<Pair<Pair<String, Boolean>, Integer>> ranking;
        Gson gson = new Gson();
        ranking = (ArrayList<Pair<Pair<String, Boolean>, Integer>>) gson.fromJson(result, ArrayList.class);

        int i = 1;
        for (Pair<Pair<String, Boolean>, Integer> account : ranking) {

            Label labelRate = new Label(i + " )");
            Label labelUsername = new Label(account.getKey().getKey());
            Label labelNumOfWin = new Label(String.valueOf(account.getValue()));
            rate.getChildren().add(labelRate);
            username.getChildren().add(labelUsername);
            numOfWin.getChildren().add(labelNumOfWin);
            if (account.getKey().getValue()) {
                labelUsername.setEffect(new SepiaTone());
                labelNumOfWin.setEffect(new SepiaTone());
                labelRate.setEffect(new SepiaTone());
            }
            i++;
        }
    }
}
