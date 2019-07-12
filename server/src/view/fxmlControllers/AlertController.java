package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import view.Utility;
import view.WindowChanger;

import java.io.IOException;

public class AlertController {

    private static double yLayout = Screen.getPrimary().getVisualBounds().getHeight() * 100 / 540;
    public AnchorPane carrier;
    public Text text;
    public ImageView closeButton;
    public ImageView button;
    public ImageView glowButton;
    public Boolean result;
    private boolean haveAcceptButton;
    private AnchorPane alertAnchorPane;

    public synchronized void close() {
        resetAlertInPosition();
        result = false;
        notify();
    }

    public void shineCloseButton() {
        Platform.runLater(() -> closeButton.setOpacity(0.4));
    }

    public void resetCloseButton() {
        Platform.runLater(() -> closeButton.setOpacity(0));
    }

    public synchronized void accept() {
        if (haveAcceptButton) {
            resetAlertInPosition();
            result = true;
            notify();
        }
    }

    public void shineAcceptButton() {
        if (haveAcceptButton) {
            Platform.runLater(() -> {
                glowButton.setOpacity(1);
                button.setOpacity(0);
            });
        }
    }

    public void resetAcceptButton() {
        if (haveAcceptButton) {
            Platform.runLater(() -> {
                button.setOpacity(1);
                glowButton.setOpacity(0);
            });
        }
    }

    public static synchronized void setAndShowAndDoOrNot(String text, Runnable doRunnable, Runnable orNotRunnable) {
        AlertController alertController = setAndShowAndGetResultByAnAlertController(text, true);
        new Thread(() -> {
            synchronized (alertController) {
                try {
                    alertController.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (alertController.result) doRunnable.run();
            else orNotRunnable.run();
        }).start();
    }

    public static synchronized void setAndShowAndDo(String text, Runnable runnable) {
        AlertController alertController = setAndShowAndGetResultByAnAlertController(text, true);
        new Thread(() -> {
            synchronized (alertController) {
                try {
                    alertController.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (alertController.result) runnable.run();
        }).start();

    }

    public static synchronized void setAndShow(String text) {
        setAndShowAndGetResultByAnAlertController(text, false);
    }

    private static AlertController setAndShowAndGetResultByAnAlertController(String text, boolean haveAcceptButton) {
        FXMLLoader fxmlLoader = new FXMLLoader(AlertController.class.getResource("../fxmls/alert.fxml"));
        AnchorPane alertPane = null;
        try {
            alertPane = (AnchorPane) Utility.scale(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertController alertController = fxmlLoader.getController();
        alertController.alertAnchorPane = alertPane;
        alertController.text.setText(text);
        alertController.haveAcceptButton = haveAcceptButton;
        if (!haveAcceptButton) alertController.button.setOpacity(0);
        alertController.carrier.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight());
        WindowChanger.instance.addAnAdditionalParent(alertPane);
        alertController.setAlertInPosition();
        return alertController;
    }

    private void setAlertInPosition() {
        new Thread(() -> {
            while (carrier.getLayoutY() > yLayout) {
                double newYLayout = carrier.getLayoutY() - 10 > yLayout ? carrier.getLayoutY() - 10 : yLayout;
                Platform.runLater(() -> carrier.setLayoutY(newYLayout));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void resetAlertInPosition() {
        new Thread(() -> {
            synchronized (AlertController.class) {
                while (carrier.getLayoutY() < Screen.getPrimary().getVisualBounds().getHeight()) {
                    double newYLayout = carrier.getLayoutY() + 10 < Screen.getPrimary().getVisualBounds().getHeight() ?
                            carrier.getLayoutY() + 10 : Screen.getPrimary().getVisualBounds().getHeight();
                    Platform.runLater(() -> carrier.setLayoutY(newYLayout));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                WindowChanger.instance.removeAdditionalParent(alertAnchorPane);
            }
        }).start();
    }
}
