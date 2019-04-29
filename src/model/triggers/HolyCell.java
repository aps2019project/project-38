package model.triggers;

import model.Constant;
import model.conditions.HasWarriorOnIt;
import model.effects.Dispelablity;
import model.gamestate.GameState;

public class HolyCell extends Trigger {
    {
        conditions.add(new HasWarriorOnIt());
        triggers.add(new HolyBuff(1, Dispelablity.GOOD,
                Constant.EffectsTriggersConstants.HolyBuff.holyBuffReducedDamage));
    }

    public HolyCell(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void apply(GameState gameState) {
        getCell().getWarrior().getTriggers().addAll(triggers);
    }
}
