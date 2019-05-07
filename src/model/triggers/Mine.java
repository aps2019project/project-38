package model.triggers;

import model.actions.Applier;
import model.conditions.HasTurnEnded;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.gamestate.MoveState;
import model.targets.OnCellGetter;

public class Mine extends Trigger {
    {
        conditions.add(new HasWarriorOnIt());
        conditions.add(new HasTurnEnded().or((gameState, trigger, triggerOwner) -> (gameState instanceof MoveState) && ((MoveState)gameState).getTargetCell().equals(triggerOwner)));
        actions.put(new Applier(),new OnCellGetter());
    }
    public Mine(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }
}
