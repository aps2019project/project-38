package view.fxmlControllers;

import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class WarriorCartController {
    public ImageView blueLine;
    public ImageView cartImage;
    public Text apText;
    public Text hpText;
    public AnchorPane gifPane;
    public ImageView pricePlace;
    public Text priceText;
    public ImageView blueDot;
    public ImageView blueDiamond;
    public Text manaText;
    public Text nameText;
    public Text typeText;
    public Text descriptionText;

    public void selectCart(MouseEvent mouseEvent) {
    }

    public void shineCart(MouseEvent mouseEvent) {
        blueLine.setOpacity(1);
        cartImage.setEffect(new Glow(0.3));
        blueDiamond.setEffect(new Glow(0.3));
        blueDot.setEffect(new Glow(0.3));
        pricePlace.setEffect(new Glow(0.3));
        gifPane.setEffect(new Glow(0.3));
    }

    public void resetCart(MouseEvent mouseEvent) {
        blueLine.setOpacity(0);
        cartImage.setEffect(null);
        blueDiamond.setEffect(null);
        blueDot.setEffect(null);
        pricePlace.setEffect(null);
        gifPane.setEffect(null);
    }
}
