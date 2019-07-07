package model.conditions;

import model.QualityHaver;
import model.gamestate.GameState;
import model.gamestate.MoveState;
import model.triggers.Trigger;

public class HasMoved implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof MoveState)){
            return false;
        }
        MoveState moveState = (MoveState)gameState;

        return moveState.getWarrior().equals(triggerOwner);
    }
}
