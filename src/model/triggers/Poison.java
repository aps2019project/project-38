package model.triggers;

import model.Constant;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class Poison extends Trigger {
    {
        conditions.add(new HasWarriorOnIt());
        triggers.add(new Poisoned(Constant.EffectsTriggersConstants.CellPoison.poisonBuffDuration,
                Dispelablity.BAD));
    }

    public Poison(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void apply(GameState gameState) {
        getCell().getWarrior().triggers.addAll(triggers);
    }
}
