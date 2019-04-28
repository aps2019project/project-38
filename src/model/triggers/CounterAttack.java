package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.HasBeenAttacked;
import model.conditions.CanCounterAttack;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;
/*
this triggers should be added to all minions, with -1 duration.
 */
public class CounterAttack extends Trigger {
    {
        conditions.add(new HasBeenAttacked());
//        conditions.put(new HasBeenDisarmed(), false);    //should be checked in attack. as well as stun!
        conditions.add(new CanCounterAttack());
    }

    public CounterAttack(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Attack attack=(Attack)gameState;
        getWarrior().getCell().getGame().attack(attack.getAttacked().getCell(),
                attack.getAttacker().getCell());
    }
}
