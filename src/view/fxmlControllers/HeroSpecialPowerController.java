package view.fxmlControllers;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.player.Player;
import view.images.LoadedImages;
import view.visualentities.VisualSpell;

public class HeroSpecialPowerController extends Holder {
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
        if(player.getMainDeck().getHero().getPower()!=null) {
            VisualSpell vs = new VisualSpell(player.getMainDeck().getHero().getPower().getName());
            put(vs.view, vs.getWidth(), vs.getHeight());
            neededMana.setText(String.valueOf(player.getMainDeck().getHero().getPower().getRequiredMana()));
            remainedTurn.setText(String.valueOf(player.getMainDeck().getHero().getPower().coolDownRemaining));
        }
    }
}
