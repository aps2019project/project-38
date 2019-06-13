package view.fxmlControllers;

import controller.Main;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.fxmls.LoadedScenes;

public class CollectionOfShopController {
    public ImageView backGround;
    public ScrollPane scrollPane;
    public HBox hbox;
    public VBox leftVBox;
    public VBox rightVBox;
    public ImageView backButton;

    public void scrollScrollPane(ScrollEvent scrollEvent) {
    }

    public void moveScrollPane(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Up")) {

        } else if (keyEvent.getCode().getName().equals("Down")) {

        }
    }

    public void back(MouseEvent mouseEvent) {
        Main.mainStage.setScene(LoadedScenes.shop);
        Main.mainStage.setFullScreen(true);
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow());
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    public void recalculateCarts(InputMethodEvent inputMethodEvent) {
    }
}
