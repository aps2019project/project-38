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
            ImageView temp = new ImageView(new Image(new FileInputStream("src/view/images/sprites/" + name + ".png")));
            temp.setVisible(false);
            view = temp;
            animation = new SpriteAnimation(view, SpriteType.spellBreathing, name, -1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        view.setOnMouseEntered(event -> idle());
        view.setOnMouseExited(event -> breathing());
    }

    public void breathing() {
        animation.stop();
        animation = new SpriteAnimation(view, SpriteType.spellBreathing, name, -1);
    }

    public void idle() {
        animation.stop();
        animation = new SpriteAnimation(view, SpriteType.active, name, -1);
    }
}
