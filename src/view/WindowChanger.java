package view;

import controller.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.fxmlControllers.AlertController;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.util.ArrayList;

public class WindowChanger {
    public static final WindowChanger instance = new WindowChanger();
//    private ArrayList<AnchorPane> anchorPanes = new ArrayList<>();
//    private AnchorPane additionalAnchorPane;
    private Stage mainStage;
    private ArrayList<Stage> newStages = new ArrayList<>();

    private WindowChanger() {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmls/mainWindow.fxml"));
//        try {
//            Main.mainStage.setScene(new Scene(scale(fxmlLoader.load())));
//            Main.mainStage.setFullScreen(true);
//            MainAnchorPaneController mainAnchorPaneController = fxmlLoader.getController();
//            anchorPanes.add(mainAnchorPaneController.anchorPaneOne);
//            anchorPanes.add(mainAnchorPaneController.anchorPaneTwo);
//            anchorPanes.get(1).setLayoutY(Screen.getPrimary().getVisualBounds().getHeight());
//            additionalAnchorPane = mainAnchorPaneController.additionalAnchorPane;
//            additionalAnchorPane.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        AnchorPane mainAnchorPane = new AnchorPane();
        Platform.runLater(() -> {
            mainAnchorPane.setPrefSize(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
            mainAnchorPane.setMaxSize(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
            mainAnchorPane.setMinSize(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        });
        Main.mainStage.setScene(new Scene(mainAnchorPane));
        mainStage = Main.mainStage;
    }

    public void setNewScene(Pane pane) {
//        new Thread(() -> {
//            Platform.runLater(() -> {
//                anchorPanes.get(1).getChildren().clear();
//                anchorPanes.get(1).getChildren().add(pane);
//            });
//            while (anchorPanes.get(1).getLayoutY() > 0 || anchorPanes.get(0).getLayoutY() > -1 * Screen.getPrimary().getVisualBounds().getHeight()) {
//                double newPaneY = anchorPanes.get(0).getLayoutY() + Screen.getPrimary().getVisualBounds().getHeight() - 30 > 0 ? anchorPanes.get(0).getLayoutY() + Screen.getPrimary().getVisualBounds().getHeight() - 30 : 0;
//                double oldPaneY = anchorPanes.get(0).getLayoutY() - 30 > -1 * Screen.getPrimary().getVisualBounds().getHeight() ? anchorPanes.get(0).getLayoutY() - 30 : -1 * Screen.getPrimary().getVisualBounds().getHeight();
//                System.out.println(newPaneY - oldPaneY + " " + anchorPanes.get(0).getChildren().size());
//                System.out.flush();
//                Platform.runLater(() -> {
//                    anchorPanes.get(1).setLayoutY(newPaneY);
//                    anchorPanes.get(0).setLayoutY(oldPaneY);
//                });
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            AnchorPane oldAnchorPane = anchorPanes.get(0);
//            Platform.runLater(() -> oldAnchorPane.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight()));
//            anchorPanes.add(anchorPanes.get(0));
//            anchorPanes.remove(0);
//        }).start();
        Main.mainStage.getScene().setRoot(pane);
    }

    public synchronized void addNewScene(Pane pane) {
//        new Thread(() -> {
//            Platform.runLater(() -> {
//                additionalAnchorPane.getChildren().clear();
//                additionalAnchorPane.getChildren().add(pane);
//            });
//            while (additionalAnchorPane.getLayoutY() > 0) {
//                double newLayoutY = additionalAnchorPane.getLayoutY() - 10 > 0 ? additionalAnchorPane.getLayoutY() - 10 : 0;
//                Platform.runLater(() -> additionalAnchorPane.setLayoutY(newLayoutY));
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            Platform.runLater(() -> anchorPanes.get(0).setEffect(new SepiaTone(1)));
//        }).start();

        Platform.runLater(() -> {
            Stage stage = new Stage();
            Scene scene = new Scene(pane);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            newStages.add(stage);
            stage.show();
        });

//        Platform.runLater(() -> {
//            Stage stage = new Stage();
//
//            FXMLLoader fxmlLoader = new FXMLLoader(AlertController.class.getResource("../fxmls/alert.fxml"));
//            AnchorPane alertPane = null;
//            try {
//                alertPane = fxmlLoader.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////            AlertController alertController = fxmlLoader.getController();
////            alertController.carrier.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight());
//
//            FXMLLoader fxmlLoader1 = new FXMLLoader(LoadedScenes.class.getResource("gamePreviewButton.fxml"));
//            Pane myPane = null;
//            try {
//                myPane = fxmlLoader1.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            Scene scene = new Scene(alertPane);
//
//            stage.initStyle(StageStyle.TRANSPARENT);
//            scene.setFill(Color.TRANSPARENT);
//
//            stage.setScene(scene);
////            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
////            stage.setFullScreen(true);//bashe nabashe mohem nis
//            stage.show();
//        });
    }

    public synchronized void removeAdditionalScene() {
//        new Thread(() -> {
//            while (additionalAnchorPane.getLayoutY() < Screen.getPrimary().getVisualBounds().getHeight()) {
//                double newLayoutY = additionalAnchorPane.getLayoutY() + 10 < Screen.getPrimary().getVisualBounds().getHeight() ?
//                        additionalAnchorPane.getLayoutY() + 10 : Screen.getPrimary().getVisualBounds().getHeight();
//                Platform.runLater(() -> additionalAnchorPane.setLayoutY(newLayoutY));
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            Platform.runLater(() -> {
//                additionalAnchorPane.getChildren().clear();
//                anchorPanes.get(0).setEffect(null);
//            });
//        }).start();
        Stage stage = newStages.get(0);
        Platform.runLater(() -> {
            stage.close();
            mainStage.setFullScreen(true);
        });
        newStages.remove(0);
    }

    Parent backup = null;
    public void adjustGameScene(boolean enter) {
        if (enter) {
            backup = Main.mainStage.getScene().getRoot();
            Main.mainStage.getScene().setRoot(LoadedScenes.arena);
        } else {
            Main.mainStage.getScene().setRoot(backup);
        }
    }
}
