package model.triggers;

import model.Constant;
import model.cards.warriors.Warrior;
import model.conditions.HasAttacked;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;

public class WoundDeepener extends Trigger {
    {
        conditions.add(new HasAttacked());
    }

    public WoundDeepener(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Attack attack = (Attack) gameState;
        getWarrior().triggers.add(new AttackAdvantage(-1,Dispelablity.GOOD,
                Constant.EffectsTriggersConstants.WoundDeepener.additionalDamage,attack.getAttacked()));
    }
}