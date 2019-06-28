package view;

import controller.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import view.fxmlControllers.MainAnchorPaneController;

import java.io.IOException;
import java.util.ArrayList;

import static view.Utility.scale;

public class WindowChanger {
    public static final WindowChanger instance = new WindowChanger();
    private ArrayList<AnchorPane> anchorPanes = new ArrayList<>();

    private WindowChanger() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmls/mainWindow.fxml"));
        try {
            Main.mainStage.setScene(new Scene(scale(fxmlLoader.load())));
            Main.mainStage.setFullScreen(true);
            MainAnchorPaneController mainAnchorPaneController = fxmlLoader.getController();
            anchorPanes.add(mainAnchorPaneController.anchorPaneOne);
            anchorPanes.add(mainAnchorPaneController.anchorPaneTwo);
            anchorPanes.get(1).setLayoutY(Screen.getPrimary().getVisualBounds().getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNewScene(Pane pane) {
//        Platform.runLater(() -> {
//            anchorPanes.get(1).getChildren().removeAll(anchorPanes.get(1).getChildren());
//            anchorPanes.get(1).getChildren().add(pane);
//            while (anchorPanes.get(1).getLayoutY() > 0 || anchorPanes.get(0).getLayoutY() > -1 * Screen.getPrimary().getVisualBounds().getHeight()) {
//                double newPaneY = anchorPanes.get(1).getLayoutY() - 10 > 0 ? anchorPanes.get(1).getLayoutY() - 10 : 0;
//                double oldPaneY = anchorPanes.get(0).getLayoutY() - 10 > -1 * Screen.getPrimary().getVisualBounds().getHeight() ? anchorPanes.get(0).getLayoutY() - 10 : -1 * Screen.getPrimary().getVisualBounds().getHeight();
//                anchorPanes.get(1).setLayoutY(newPaneY);
//                anchorPanes.get(0).setLayoutY(oldPaneY);
//            }
//            anchorPanes.get(0).setLayoutY(Screen.getPrimary().getVisualBounds().getHeight());
//            anchorPanes.add(anchorPanes.get(0));
//            anchorPanes.remove(0);
//        });
        Main.mainStage.getScene().setRoot(pane);
    }
}
