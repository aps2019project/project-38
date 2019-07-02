package view.fxmlControllers;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Game;
import model.cards.HeroPower;
import model.cards.Spell;
import model.player.Player;
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

    public void setHeroSpecialPowerFirstInfo(Player player) {
        if (player.getMainDeck().getHero().getPower() != null) {
            HeroPower spell = player.getMainDeck().getHero().getPower();
            VisualSpell vs = new VisualSpell(spell.getName());
            put(vs.view, vs.getWidth(), vs.getHeight());
            neededMana.setText(String.valueOf(spell.getRequiredMana()));
            remainedTurn.setText(String.valueOf(spell.coolDownRemaining));

            vs.view.setOnMouseClicked(event -> {
                ArenaController.ac.game.getSelectionManager().selectSpecialPower();
            });

            vs.view.setOnMouseEntered(event -> {
                vs.idle();
                ArenaController.ac.showInfoOfACard(spell.getName(),spell.description.getDescriptionOfCardSpecialAbility(),"spell",0,0);
            });

            vs.view.setOnMouseExited(event -> {
                vs.breathing();
                ArenaController.ac.endShowInfoOfACard();
            });
        }
    }
}
