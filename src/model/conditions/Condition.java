package model.conditions;

import model.gamestate.GameState;
import model.triggers.Trigger;

public abstract class Condition {
    public abstract boolean check(GameState gameState, Trigger trigger);
}
