package model.conditions;

import model.QualityHaver;
import model.gamestate.GameState;
import model.gamestate.PutMinion;
import model.triggers.Trigger;

public class HasSpawned implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof PutMinion)){
            return false;
        }
        PutMinion putMinion = (PutMinion)gameState;

        return putMinion.getWarrior().equals(triggerOwner);
    }
}
