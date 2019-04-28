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
        conditions.add(new Attacked());
    }

    public AfterAttackModifiers(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }


    @Override
    void apply(GameState gameState) {
        Attack attack = (Attack) gameState;

        attack.getAttacked().getEffects().addAll(effects);
        addTriggersToWarriorFromTrigger(attack.getAttacked(),triggers);
    }
}
