package model.triggers;

import model.Constant;
import model.QualityHaver;
import model.conditions.HasTurnEnded;
import model.effects.Dispelablity;
import model.effects.HP;
import model.gamestate.GameState;
//special because so common. and it has anti.
public class Poisoned extends Trigger{
    {
        conditions.add(new HasTurnEnded());
        effects.add(new HP(-1, Dispelablity.UNDISPELLABLE, -Constant.EffectsTriggersConstants.PoisonBuff.poisonBuffDamage));
    }

    public Poisoned(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        owner.getEffects().addAll(effects);
    }
}
