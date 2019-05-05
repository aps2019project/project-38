package model.targets;

import model.QualityHaver;
import model.cards.Hero;
import model.cards.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class HeroGetter implements TriggerTarget{
    private boolean friendMod;

    public HeroGetter(boolean friendMod) {
        this.friendMod = friendMod;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior)triggerOwner;
        ArrayList<Hero> target = new ArrayList<>();
        if(friendMod){
            target.add(warrior.getCell().getBoard().getGame().getWarriorsPlayer(warrior).getPlayerHero());
        }else {
            target.add(warrior.getCell().getBoard().getGame().getWarriorsEnemyPlayer(warrior).getPlayerHero());
        }
        return target;
    }
}
