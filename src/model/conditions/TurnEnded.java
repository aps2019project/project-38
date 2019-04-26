package model.conditions;

import model.gamestate.GameState;
import model.gamestate.TurnEnd;
import model.triggers.Trigger;

public class TurnEnded implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        return gameState instanceof TurnEnd;
    }
}
