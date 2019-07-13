package view;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import server.ServerInit;

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
        ServerInit.mainStage.setScene(new Scene(mainAnchorPane));
        ServerInit.mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public void setMainParent(Parent parent) {
        Platform.runLater(() -> {
            if (parents.size() > 0) {
                parent.setEffect(parents.get(0).getEffect());
                mainAnchorPane.getChildren().remove(parents.get(0));
                mainAnchorPane.getChildren().add(0, parent);
                parents.remove(0);
                parents.add(0, parent);
            }
            else {
                mainAnchorPane.getChildren().add(parent);
                parents.add(parent);
            }
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

    public boolean equalsToMainParent(Parent parent) {
        return mainAnchorPane.getChildren().get(0).equals(parent);
    }
}
