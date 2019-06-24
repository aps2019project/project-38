package view.fxmlControllers;

import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CardHolder {
    public Pane card;
    public ImageView cardBackGround;
    public ImageView manaBackGround;
    public Label neededManaForCart;
    public Pane gif;
    boolean isThisCardComing;

    public void changeBackGround_Enter() {
        if (isThisCardComing) return;
        cardBackGround.setEffect(new BoxBlur());

    }

    public void changeBackGround_Exit() {
        if (isThisCardComing) return;
        cardBackGround.setEffect(null);
    }
}
