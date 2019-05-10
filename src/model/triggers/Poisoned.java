package model.triggers;

import model.Constant;
import model.QualityHaver;
import model.actions.Applier;
import model.conditions.HasTurnEnded;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.GameState;
import model.targets.TriggerOwnerGetter;

//special because so common. and it has anti.
public class Poisoned extends Trigger{
    {
        conditions.add(new HasTurnEnded());
        effects.add(new HP(-1, Dispelablity.UNDISPELLABLE, -Constant.EffectsTriggersConstants.PoisonBuff.poisonBuffDamage));
        actions.put(new Applier(),new TriggerOwnerGetter());
    }

    public Poisoned(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }
}
