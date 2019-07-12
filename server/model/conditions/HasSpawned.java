package model.conditions;

import model.QualityHaver;
import model.gamestate.GameState;
import model.gamestate.PutMinionState;
import model.triggers.Trigger;

public class HasSpawned implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof PutMinionState)){
            return false;
        }
        PutMinionState putMinionState = (PutMinionState)gameState;

        return putMinionState.getWarrior().equals(triggerOwner);
    }
}
