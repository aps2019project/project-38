package model.conditions;

import model.gamestate.GameState;
import model.gamestate.Move;
import model.triggers.Trigger;

public class Moved implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        if(!(gameState instanceof Move)){
            return false;
        }
        Move move = (Move)gameState;

        return move.getWarrior()==trigger.getWarrior();
    }
}
