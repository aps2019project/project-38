package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.io.IOException;

public class AlertController {
    private static double yLayout = Screen.getPrimary().getVisualBounds().getHeight() * 100 / 540;
    public AnchorPane carrier;
    public Text text;
    public ImageView button;
    public ImageView glowButton;
    public Boolean result;
    private boolean haveAcceptButton;

    public synchronized void close(MouseEvent mouseEvent) {
        resetAlertInPosition();
        result = false;
        WindowChanger.instance.removeAdditionalScene();
        notify();
    }

    public synchronized void accept(MouseEvent mouseEvent) {
        if (haveAcceptButton) {
            resetAlertInPosition();
            result = true;
            WindowChanger.instance.removeAdditionalScene();
            notify();
        }
    }

    public void glowButton(MouseEvent mouseEvent) {
        if (haveAcceptButton) {
            Platform.runLater(() -> {
                glowButton.setOpacity(1);
                button.setOpacity(0);
            });
        }
    }

    public void resetButton(MouseEvent mouseEvent) {
        if (haveAcceptButton) {
            Platform.runLater(() -> {
                button.setOpacity(1);
                glowButton.setOpacity(0);
            });
        }
    }

    public static AlertController setAndShowAndWaitToGetResult (String text, boolean haveAcceptButton) {
        FXMLLoader fxmlLoader = new FXMLLoader(AlertController.class.getResource("../fxmls/alert.fxml"));
        AnchorPane alertPane = null;
        try {
            alertPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertController alertController = fxmlLoader.getController();
        alertController.text.setText(text);
        alertController.haveAcceptButton = haveAcceptButton;
        if (!haveAcceptButton) alertController.button.setOpacity(0);
        alertController.carrier.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight());
        WindowChanger.instance.addNewScene(alertPane);
        alertController.setAlertInPosition();
        return alertController;
    }

    private void setAlertInPosition() {
        Platform.runLater(() -> {
            while (carrier.getLayoutY() > yLayout) {
                carrier.setLayoutY(carrier.getLayoutY() - 1 > yLayout ? carrier.getLayoutY() - 1: yLayout);
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void resetAlertInPosition() {
        Platform.runLater(() -> {
            while (carrier.getLayoutY() < Screen.getPrimary().getVisualBounds().getHeight()) {
                double newYLayout = carrier.getLayoutY() + 1 < Screen.getPrimary().getVisualBounds().getHeight() ?
                        carrier.getLayoutY() + 1: Screen.getPrimary().getVisualBounds().getHeight();
                carrier.setLayoutY(newYLayout);
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
