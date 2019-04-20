package model.conditions;

import model.gamestate.Death;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class Died implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        if(!(gameState instanceof Death)){
            return false;
        }

        return ((Death)gameState).getWarrior()==trigger.getWarrior();
    }
}
