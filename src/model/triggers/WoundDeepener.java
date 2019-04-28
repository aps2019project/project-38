package model.triggers;

import model.Constant;
import model.cards.warriors.Warrior;
import model.conditions.Attacked;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;

public class WoundDeepener extends Trigger {
    {
        conditions.add(new Attacked());
    }

    public WoundDeepener(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Attack attack = (Attack) gameState;
        getWarrior().getTriggers().add(new AttackAdvantage(getWarrior(),-1,Dispelablity.GOOD,
                Constant.EffectsTriggersConstants.WoundDeepener.additionalDamage,attack.getAttacked()));
    }
}
