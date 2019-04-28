package model.triggers;

import model.cards.warriors.Warrior;
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
    void apply(GameState gameState) {
        Attack attack = (Attack) gameState;

        attack.getAttacked().effects.addAll(effects);
        attack.getAttacked().triggers.addAll(triggers);
    }
}
