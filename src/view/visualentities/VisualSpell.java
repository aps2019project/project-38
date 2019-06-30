package view.visualentities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.cards.Card;
import model.cards.Spell;
import model.cards.Warrior;
import view.fxmlControllers.ArenaController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
            ImageView temp = new ImageView(new Image(new FileInputStream("src/view/images/sprites/" + name + ".png")));
            temp.setVisible(false);
            view = temp;
            animation = new SpriteAnimation(view, SpriteType.spellBreathing, name, -1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        view.setOnMouseEntered(event -> {
            idle();
            if (ArenaController.ac.game != null) {
                Spell theCard =(Spell) Card.getAllCards().values().stream().filter(card -> card.getName().equals(name)).findAny().orElse(null);
                if(theCard==null){
                    return;
                }
                ArenaController.ac.showInfoOfACard(name,theCard.description.getDescriptionOfCardSpecialAbility(),"Minion",view,animation.width,animation.height,0,0);

            }
        });
        view.setOnMouseExited(event -> {
            breathing();
            if (ArenaController.ac.game != null) {
                ArenaController.ac.endShowInfoOfACard();
            }
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
