package model.conditions;

import model.QualityHaver;
import model.gamestate.GameState;
import model.gamestate.TurnEndState;
import model.triggers.Trigger;

public class HasTurnEnded implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        return gameState instanceof TurnEndState;
    }
}
