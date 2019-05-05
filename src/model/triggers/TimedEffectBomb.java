package model.triggers;

import model.actions.Applier;
import model.conditions.HasTurnEnded;
import model.effects.Dispelablity;
import model.targets.TriggerOwnerGetter;

//special because of special condition.
//add effect that you want to be added at the end of turn, in "duration" turns.
public class TimedEffectBomb extends Trigger {
    {
        conditions.add(new HasTurnEnded());
        conditions.add(((gameState, trigger,triggerOwner) -> duration == 1));
        actions.put(new Applier(),new TriggerOwnerGetter());
    }

    public TimedEffectBomb(int duration, Dispelablity dispelablity) {
        super(duration+1, dispelablity);
    }
}
