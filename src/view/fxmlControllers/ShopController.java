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
import view.fxmls.LoadedPanes;

public class ShopController {
    public ImageView background;
    public ImageView back;
    public ImageView collection;
    public ScrollPane scrollPane;
    public HBox hBox;
    public VBox leftVBox;
    public VBox rightVBox;

    public void moveScrollPane(KeyEvent keyEvent) {
    }

    public void scrollScrollPane(ScrollEvent scrollEvent) {
    }

    public void back(MouseEvent mouseEvent) {
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        back.setEffect(new Glow(0.5));
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        back.setEffect(null);
    }

    public void goToCollection(MouseEvent mouseEvent) {
        Main.mainStage.setScene(LoadedPanes.collectionOfShop);
    }

    public void shineCollectionBottom(MouseEvent mouseEvent) {
        collection.setEffect(new Glow(0.5));
    }

    public void resetCollectionBottom(MouseEvent mouseEvent) {
        collection.setEffect(null);
    }
}
