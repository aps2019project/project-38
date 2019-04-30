package model.conditions;

import model.QualityHaver;
import model.gamestate.Death;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class HasDied implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof Death)){
            return false;
        }

        return ((Death)gameState).getWarrior().equals(triggerOwner);
    }
}
