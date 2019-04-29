package model.triggers;

import model.Constant;
import model.conditions.HasAttacked;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;
//special because the trigger in it needs gameState to be built.
public class WoundDeepener extends Trigger {
    {
        conditions.add(new HasAttacked());
    }

    public WoundDeepener(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void apply(GameState gameState) {
        Attack attack = (Attack) gameState;
        getWarrior().getTriggers().add(new AttackAdvantage(-1,Dispelablity.GOOD,
                Constant.EffectsTriggersConstants.WoundDeepener.additionalDamage,attack.getAttacked()));
    }
}