package model.conditions;

import model.gamestate.GameState;
import model.gamestate.Move;
import model.triggers.Trigger;

public class HasWarriorOnIt implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        return trigger.getCell().getWarrior() != null;
    }
}
