package model.conditions;

import model.gamestate.Attack;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class BeingAttacked implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        if(!(gameState instanceof Attack)){
            return false;
        }
        Attack attack=(Attack)gameState;

        return attack.getAttecked() == trigger.getWarrior() && attack.isPending();
    }
}
