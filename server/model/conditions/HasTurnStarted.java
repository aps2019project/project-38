package model.conditions;

import model.QualityHaver;
import model.gamestate.GameState;
import model.gamestate.TurnStartState;
import model.triggers.Trigger;

public class HasTurnStarted implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        return gameState instanceof TurnStartState;
    }
}
