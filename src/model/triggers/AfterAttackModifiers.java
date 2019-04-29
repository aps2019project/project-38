package model.triggers;

import model.QualityHaver;
import model.conditions.HasAttacked;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;
/*
this trigger adds all triggers and effects in it to the warrior that this warrior attacks.
 */
public class AfterAttackModifiers extends Trigger {
    {
        conditions.add(new HasAttacked());
    }

    public AfterAttackModifiers(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        Attack attack = (Attack) gameState;

        attack.getAttacked().getEffects().addAll(effects);
        attack.getAttacked().getTriggers().addAll(triggers);
    }
}
