package model.triggers;

import model.actions.triggeractions.Applier;
import model.conditions.HasTurnEnded;
import model.effects.Dispelablity;
import model.targets.triggertargets.TriggerOwnerGetter;

//special because of special condition.
public class TimedEffectBomb extends Trigger {
    {
        conditions.add(new HasTurnEnded());
        conditions.add(((gameState, trigger,triggerOwner) -> duration == 1));
        actions.put(new Applier(),new TriggerOwnerGetter());
    }

    public TimedEffectBomb(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }
}
