package view.visualentities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.cards.Card;
import model.cards.CardFactory;
import model.cards.Spell;
import model.cards.Warrior;
import view.fxmlControllers.ArenaController;
import view.images.LoadedImages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

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

        if (Card.getAllCards().values().stream().filter(Objects::nonNull).anyMatch(card -> String.valueOf(card.getID()).startsWith("5") && card.getName().equals(name))) {
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
