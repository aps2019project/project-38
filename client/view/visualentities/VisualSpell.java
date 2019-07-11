package view.visualentities;

import javafx.scene.image.ImageView;
import view.images.LoadedImages;

public class VisualSpell {
    String name;
    String fileName;
    public ImageView view;
    public SpriteAnimation animation;

    public double getWidth() {
        return animation.getWidth();
    }

    public double getHeight() {
        return animation.getHeight();
    }

    public VisualSpell(String name) {
        this.name = name;
        fileName = name;

        if (LoadedImages.sprites.get(fileName) == null) {
            fileName = "cs";
        }

        ImageView temp = new ImageView(LoadedImages.sprites.get(fileName));
        temp.setVisible(false);
        view = temp;
        animation = new SpriteAnimation(view, SpriteType.spellBreathing, fileName, -1);

        view.setOnMouseEntered(event -> {
            idle();
        });
        view.setOnMouseExited(event -> {
            breathing();
        });
    }

    public void breathing() {
        animation.stop();
        animation = new SpriteAnimation(view, SpriteType.spellBreathing, fileName, -1);
    }

    public void idle() {
        animation.stop();
        animation = new SpriteAnimation(view, SpriteType.active, fileName, -1);
    }
}
