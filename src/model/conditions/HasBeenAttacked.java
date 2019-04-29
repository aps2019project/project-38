package model.conditions;

import model.QualityHaver;
import model.gamestate.Attack;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class HasBeenAttacked implements Condition {

    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof Attack)){
            return false;
        }
        Attack attack=(Attack)gameState;

        return attack.getAttacked().equals(triggerOwner) && !attack.isPending();
    }
}
