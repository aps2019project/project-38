package view;

import controller.Client;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

import java.util.ArrayList;

public class WindowChanger {
    public static final WindowChanger instance = new WindowChanger();
    private AnchorPane mainAnchorPane;
    private ArrayList<Parent> parents = new ArrayList<>();

    private WindowChanger() {
        mainAnchorPane = new AnchorPane();
        Platform.runLater(() -> {
            mainAnchorPane.setPrefSize(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
            mainAnchorPane.setMaxSize(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
            mainAnchorPane.setMinSize(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        });
        Client.mainStage.setScene(new Scene(mainAnchorPane));
        Client.mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public void setMainParent(Parent pane) {
        Platform.runLater(() -> {
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(pane);
            parents.clear();
            parents.add(pane);
        });
    }

    public synchronized void addAnAdditionalParent(Parent newAdditionalParent) {
        for (Parent parent : parents) {
            Platform.runLater(() -> parent.setEffect(new BoxBlur()));
        }
        Platform.runLater(() -> {
            mainAnchorPane.getChildren().add(newAdditionalParent);
            parents.add(newAdditionalParent);
        });
    }

    public synchronized void removeAdditionalParent(Parent additionalParent) {
        if (parents.indexOf(additionalParent) == parents.size() - 1)
            Platform.runLater(() -> parents.get(parents.size() - 2).setEffect(null));
        Platform.runLater(() -> {
            mainAnchorPane.getChildren().remove(additionalParent);
            parents.remove(additionalParent);
        });
    }
}
