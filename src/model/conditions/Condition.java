package model.conditions;

import model.gamestate.GameState;
import model.triggers.Trigger;

public interface Condition {
    boolean check(GameState gameState, Trigger trigger);
}
