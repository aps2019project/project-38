package view.fxmlControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Account;
import model.MatchHistory;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable {
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
        ArrayList<Account> allAccounts = Account.sortAccounts();
        for (int i = 1; i < rate.getChildren().size(); i++) {
            rate.getChildren().remove(rate.getChildren().get(i));
            username.getChildren().remove(username.getChildren().get(i));
            numOfWin.getChildren().remove(numOfWin.getChildren().get(i));
        }
        int i = 1;
        for (Account account : allAccounts) {
            int numberOfWins = 0;
            for (MatchHistory matchHistory : account.getHistory()) {
                if (matchHistory.getDidWin()) numberOfWins++;
            }
            Label labelRate = new Label(i + " )");
            Label labelUsername = new Label(account.getUsername());
            Label labelNumOfWin = new Label(String.valueOf(numberOfWins));
            rate.getChildren().add(labelRate);
            username.getChildren().add(labelUsername);
            numOfWin.getChildren().add(labelNumOfWin);
            i++;
        }
    }

    public void back() {
        WindowChanger.instance.setNewScene(RegisterMenuController.getScene());
    }
}
