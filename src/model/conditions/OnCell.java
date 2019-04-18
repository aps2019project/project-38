package model.conditions;

import model.gamestate.GameState;
import model.gamestate.Move;
import model.triggers.Trigger;

public class OnCell extends Condition{
    @Override
    public boolean check(GameState gameState, Trigger trigger) {
        if(!(gameState instanceof Move)){
            return false;
        }
        Move move=(Move)gameState;

        return move.getDestinationCell() == trigger.getCell();
    }
}
