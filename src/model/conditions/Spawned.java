package model.conditions;

import model.gamestate.GameState;
import model.gamestate.PutMinion;
import model.triggers.Trigger;

public class Spawned implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        if(!(gameState instanceof PutMinion)){
            return false;
        }
        PutMinion putMinion = (PutMinion)gameState;

        return putMinion.getWarrior()==trigger.getWarrior();
    }
}
