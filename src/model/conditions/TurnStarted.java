package model.conditions;

import model.gamestate.GameState;
import model.gamestate.TurnStart;
import model.triggers.Trigger;

public class TurnStarted implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        return gameState instanceof TurnStart;
    }
}
