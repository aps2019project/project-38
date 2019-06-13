package view.fxmlControllers;

import controller.Main;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.fxmls.LoadedScenes;

public class CollectionOfShopController {
    public ImageView backGround;
    public ImageView back;
    public ScrollPane scrollPane;
    public HBox hbox;
    public VBox leftVBox;
    public VBox rightVBox;

    public void scrollScrollPane(ScrollEvent scrollEvent) {
    }

    public void moveScrollPane(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Up")) {

        } else if (keyEvent.getCode().getName().equals("Down")) {

        }
    }

    public void back(MouseEvent mouseEvent) {
        Main.mainStage.setScene(LoadedScenes.shop);
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        back.setEffect(new Glow());
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        back.setEffect(null);
    }
}
