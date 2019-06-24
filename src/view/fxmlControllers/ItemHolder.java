package view.fxmlControllers;

import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ItemHolder {
    public Pane itemHolder;
    public Pane gif;
    public ImageView itemHolderBackGround;

    public void changeBackGround_Enter() {
        itemHolderBackGround.setEffect(new BoxBlur());
    }

    public void changeBackGround_Exit() {
        itemHolderBackGround.setEffect(null);
    }
}