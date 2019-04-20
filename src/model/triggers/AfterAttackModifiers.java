package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.Attacked;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;
/*
this trigger adds all triggers and effects in it to the warrior that this warrior attacks.
 */
public class AfterAttackModifiers extends Trigger {
    {
        conditions.put(new Attacked(),true);
    }

    public AfterAttackModifiers(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }


    @Override
    void apply(GameState gameState) {
        Attack attack = (Attack) gameState;

        attack.getAttecked().effects.addAll(effects);
        addTriggers(attack.getAttecked(),triggers);
    }
}
