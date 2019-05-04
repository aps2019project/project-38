package model.conditions;

import model.QualityHaver;
import model.gamestate.DispelState;
import model.gamestate.GameState;
import model.triggers.Trigger;

public class IsBeingDispelled implements Condition {

    @Override
    public boolean check(GameState gameState, Trigger trigger, QualityHaver triggerOwner) {
        if(!(gameState instanceof DispelState)){
            return false;
        }

        DispelState dispelState = (DispelState)gameState;
        return dispelState.pending && dispelState.getBeingDispelled().equals(trigger);
    }
}
