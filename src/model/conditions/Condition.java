package model.conditions;

import model.gamestate.GameState;
import model.triggers.Trigger;

import java.util.function.Predicate;

public interface Condition {
    boolean check(GameState gameState, Trigger trigger);

    default Condition or(Condition other) {
        return (gameState, trigger) -> this.check(gameState, trigger) || other.check(gameState, trigger);
    }

    default Condition not(){
        return ((gameState, trigger) -> !this.check(gameState,trigger));
    }
}