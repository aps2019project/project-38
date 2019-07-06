package view.visualentities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.cards.Card;
import model.cards.CardFactory;
import model.cards.Spell;
import model.cards.Warrior;
import view.fxmlControllers.ArenaController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class VisualSpell {
    String name;
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
        try {
            ImageView temp;
            if(Card.getAllCards().values().stream().filter(Objects::nonNull).anyMatch(card -> String.valueOf(card.getID()).startsWith("5"))){
                temp = new ImageView(new Image(new FileInputStream("src/view/images/sprites/cs.png")));
            }else {
                temp = new ImageView(new Image(new FileInputStream("src/view/images/sprites/" + name + ".png")));
            }
            temp.setVisible(false);
            view = temp;
            animation = new SpriteAnimation(view, SpriteType.spellBreathing, name, -1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        view.setOnMouseEntered(event -> {
            idle();
        });
        view.setOnMouseExited(event -> {
            breathing();
        });
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
