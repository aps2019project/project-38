package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.BeenAttacked;
import model.conditions.BeenDisarmed;
import model.conditions.CanCounterAttack;
import model.effects.Dispelablity;
import model.gamestate.Attack;
import model.gamestate.GameState;
/*
this triggers should be added to all minions, with -1 duration.
 */
public class CounterAttack extends Trigger {
    {
        conditions.add(new BeenAttacked());
//        conditions.put(new BeenDisarmed(), false);    //should be checked in attack. as well as stun!
        conditions.add(new CanCounterAttack());
    }

    public CounterAttack(Warrior warrior, int duration, Dispelablity dispelablity) {
        super(warrior, duration, dispelablity);
    }

    @Override
    void apply(GameState gameState) {
        Attack attack=(Attack)gameState;
        getWarrior().getCell().getGame().attack(attack.getAttecked().getCell(),
                attack.getAttacker().getCell());
    }
}
