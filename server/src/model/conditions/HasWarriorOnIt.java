package model.conditions;

import model.Cell;
import model.QualityHaver;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class HasWarriorOnIt implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(triggerOwner instanceof Cell) {
            return ((Cell) triggerOwner).getWarrior() != null;
        }else {
            return false;
        }
    }
}
