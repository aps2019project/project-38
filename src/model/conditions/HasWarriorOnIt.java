package model.conditions;

import model.Cell;
import model.QualityHaver;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class HasWarriorOnIt implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        return ((Cell)triggerOwner).getWarrior() != null;
    }
}
