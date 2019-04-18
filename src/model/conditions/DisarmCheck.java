package model.conditions;

import model.effects.Disarm;
import model.effects.Effect;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class DisarmCheck extends Condition {

    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        for (Effect effect : trigger.effects) {
            if(effect instanceof Disarm){
                return true;
            }
        }
        return false;
    }
}
