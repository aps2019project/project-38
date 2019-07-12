package model.conditions;

import model.QualityHaver;
import model.gamestate.DeathState;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class HasDied implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof DeathState)){
            return false;
        }

        return ((DeathState)gameState).getWarrior().equals(triggerOwner);
    }
}
