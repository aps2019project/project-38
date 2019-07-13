package view.fxmlControllers;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ShownSomethingInArenaController extends Holder {
    public ImageView cardImage;
    public ImageView blueDot;
    public Text nameText;
    public Text typeText;
    public Text descriptionText;



    public void setName(String name) {
        this.nameText.setText(name);
    }

    public void setType(String type) {
        this.typeText.setText(type);
    }

    public void setDescription(String description) {
        this.descriptionText.setText(description);
    }
}
