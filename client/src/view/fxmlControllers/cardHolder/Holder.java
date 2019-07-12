package view.fxmlControllers.cardHolder;

import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Holder {
    public Pane gif;
    public ImageView backGround;

    public void put(ImageView visualEntity, double width, double height) {
        gif.getChildren().add(visualEntity);
        double newXCoordinate_vm, newYCoordinate_vm;
        newXCoordinate_vm = (100 - width) / 2;
        newYCoordinate_vm = (100 - height) / 2;
        if (height >= 80) {
            newYCoordinate_vm -= 5;
        }
        if (height >= 100) {
            newYCoordinate_vm -= 10;
        }
        if (height >= 120) {
            newYCoordinate_vm -= 5;
        }
        if (height >= 130) {
            newYCoordinate_vm -= 3;
        }
        visualEntity.relocate(newXCoordinate_vm, newYCoordinate_vm);
    }

    public void changeBackGround_Enter() {
        backGround.setEffect(new BoxBlur());
    }

    public void changeBackGround_Exit() {
        backGround.setEffect(null);
    }
}
