package model.targets.triggertargets;

import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.gamestate.GameState;

import java.util.ArrayList;

public class AllWarriorsGetter implements TriggerTarget {
    private boolean friendMod;

    public AllWarriorsGetter(boolean friendMod) {
        this.friendMod = friendMod;
    }

    @Override
    public ArrayList<? extends QualityHaver> getTarget(QualityHaver triggerOwner, GameState gameState) {
        assert triggerOwner instanceof Warrior;

        Warrior warrior = (Warrior)triggerOwner;
        if(friendMod){
            return warrior.getCell().getBoard().getGame().getWarriorsPlayer(warrior).getWarriors();
        }else {
            return warrior.getCell().getBoard().getGame().getWarriorsEnemyPlayer(warrior).getWarriors();
        }
    }
}
