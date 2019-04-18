package model.conditions;

import model.gamestate.Attack;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class BeenAttacked extends Condition {

    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        if(!(gameState instanceof Attack)){
            return false;
        }
        Attack attack=(Attack)gameState;

        if(attack.getAttecked()==trigger.getWarrior()){
            return true;
        }
        return false;
    }
}
