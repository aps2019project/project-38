package view.visualentities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class VisualSpell {
    String name;
    public ImageView view;
    public SpriteAnimation animation;

    public VisualSpell(String name) {
        this.name = name;

        try {
            view = new ImageView(new Image(new FileInputStream("src/view/images/sprites/" + name + ".png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        animation = new SpriteAnimation(view, SpriteType.breathing, name, -1);

//        view.setOnMouseEntered(event -> idle());
//        view.setOnMouseExited(event -> breathing());
    }
}
