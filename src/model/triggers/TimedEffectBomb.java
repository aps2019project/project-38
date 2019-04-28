package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.HasTurnEnded;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class TimedEffectBomb extends Trigger {
    {
        conditions.add(new HasTurnEnded());
        conditions.add(((gameState, trigger) -> duration == 1));
    }

    public TimedEffectBomb(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        getWarrior().effects.addAll(effects);
        getWarrior().triggers.addAll(triggers);
    }
}
