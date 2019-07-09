package view.fxmlControllers.cardHolder;

import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import view.fxmlControllers.cardHolder.Holder;

public class HandSpriteHolderController extends Holder {
    public Label neededMana;
    public ImageView border;
    public ImageView manaBackGround;
    public boolean isThisCardComingCard;

    public void changeBackGround_Enter() {
        if (isThisCardComingCard) return;
        backGround.setEffect(new BoxBlur());
    }

    public void changeBackGround_Exit() {
        if (isThisCardComingCard) return;
        backGround.setEffect(null);
    }
}
