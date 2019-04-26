package model.conditions;

import model.gamestate.Attack;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class Attacking implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        if(!(gameState instanceof Attack)){
            return false;
        }
        Attack attack=(Attack)gameState;

        return attack.getAttacker() == trigger.getWarrior() && attack.isPending();
    }
}
