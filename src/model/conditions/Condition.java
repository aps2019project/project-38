package model.conditions;

import model.QualityHaver;
import model.gamestate.GameState;
import model.triggers.Trigger;

public interface Condition {
    boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner);

    default Condition or(Condition other) {
        return (gameState, trigger,triggerOwner) -> this.check(gameState, trigger,triggerOwner) || other.check(gameState, trigger,triggerOwner);
    }

    default Condition not(){
        return ((gameState, trigger,triggerOwner) -> !this.check(gameState,trigger,triggerOwner));
    }
}