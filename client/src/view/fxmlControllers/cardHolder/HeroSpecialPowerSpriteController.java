package view.fxmlControllers.cardHolder;

import client.net.Digikala;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.cards.HeroPower;
import view.fxmlControllers.ArenaController;
import view.images.LoadedImages;
import view.visualentities.VisualSpell;

public class HeroSpecialPowerSpriteController extends Holder {
    public Label neededMana;
    public Label remainedTurn;
    public ImageView manaBackGround;

    public void setRemainedTurn(int turnNumber) {
        remainedTurn.setText(String.valueOf(turnNumber));
        if (turnNumber == 0) {
            backGround.setImage(LoadedImages.blueCircle);
            manaBackGround.setImage(LoadedImages.blueMana);
        } else {
            backGround.setImage(LoadedImages.grayCircle);
            manaBackGround.setImage(LoadedImages.grayMana);
        }
    }

    public void setHeroSpecialPowerFirstInfo(int playerIndex) {
        HeroPower spell = Digikala.getHeroPowerOfPlayer(playerIndex);
        if (spell != null) {
            VisualSpell vs = new VisualSpell(spell.name);
            put(vs.view, vs.getWidth(), vs.getHeight());
            neededMana.setText(String.valueOf(spell.requiredMana));
            remainedTurn.setText(String.valueOf(spell.coolDownRemaining));

            vs.view.setOnMouseClicked(event -> {
                ArenaController.ac.sm.selectSpecialPower();
            });

            vs.view.setOnMouseEntered(event -> {
                vs.idle();
                ArenaController.ac.showInfoOfACard(spell.name,spell.description.descriptionOfCardSpecialAbility,"spell",0,0);
            });

            vs.view.setOnMouseExited(event -> {
                vs.breathing();
                ArenaController.ac.endShowInfoOfACard();
            });
        }
    }
}
