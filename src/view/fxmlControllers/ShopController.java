package view.fxmlControllers;

import controller.Main;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.fxmls.LoadedPanes;

public class ShopController {
    public ImageView background;
    public ScrollPane scrollPane;
    public HBox hBox;
    public VBox leftVBox;
    public VBox rightVBox;
    public ImageView goldCircleOfCollectionButton;
    public ImageView backButton;
    public ImageView collectionButton;
    public Text collectionText;

    public void moveScrollPane(KeyEvent keyEvent) {
    }

    public void scrollScrollPane(ScrollEvent scrollEvent) {
    }

    public void back(MouseEvent mouseEvent) {
    }

    public void shineBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(new Glow(0.5));
    }

    public void resetBackBottom(MouseEvent mouseEvent) {
        backButton.setEffect(null);
    }

    public void goToCollection(MouseEvent mouseEvent) {
        Main.mainStage.setScene(LoadedPanes.collectionOfShop);
        Main.mainStage.setFullScreen(true);
    }

    public void shineCollectionBottom(MouseEvent mouseEvent) {
        collectionButton.setEffect(new Glow(0.5));
        goldCircleOfCollectionButton.setOpacity(0.9);
        collectionText.setOpacity(1);
    }

    public void resetCollectionBottom(MouseEvent mouseEvent) {
        collectionButton.setEffect(null);
        goldCircleOfCollectionButton.setOpacity(0.6);
        collectionText.setOpacity(0.6);
    }
}
