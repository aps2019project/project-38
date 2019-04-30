package model.conditions;

import model.QualityHaver;
import model.triggers.Disarm;
import model.effects.Effect;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class HasBeenDisarmed implements Condition {

    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        for (Effect effect : trigger.getEffects()) {
            if(effect instanceof Disarm){
                return true;
            }
        }
        return false;
    }
}
