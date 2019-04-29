package model.triggers;

import model.QualityHaver;
import model.conditions.HasTurnEnded;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class TimedEffectBomb extends Trigger {
    {
        conditions.add(new HasTurnEnded());
        conditions.add(((gameState, trigger,triggerOwner) -> duration == 1));
    }

    public TimedEffectBomb(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void apply(GameState gameState, QualityHaver owner) {
        owner.getEffects().addAll(effects);
        owner.getTriggers().addAll(triggers);
    }
}
