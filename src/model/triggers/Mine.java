package model.triggers;

import model.actions.Applier;
import model.conditions.Condition;
import model.conditions.HasTurnEnded;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.gamestate.MoveState;
import model.targets.OnCellGetter;

import java.io.Serializable;

public class Mine extends Trigger {
    {
        conditions.add(new HasWarriorOnIt());
        conditions.add(new HasTurnEnded().or((Condition & Serializable) (gameState, trigger, triggerOwner) -> (gameState instanceof MoveState) && ((MoveState)gameState).getTargetCell().equals(triggerOwner)));
        actions.put(new Applier(),new OnCellGetter());
    }
    public Mine(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }
}
