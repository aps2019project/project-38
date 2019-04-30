package model.conditions;

import model.QualityHaver;
import model.gamestate.GameState;
import model.gamestate.Move;
import model.triggers.Trigger;

public class HasMoved implements Condition {
    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof Move)){
            return false;
        }
        Move move = (Move)gameState;

        return move.getWarrior().equals(triggerOwner);
    }
}
