package model.triggers;

import model.cards.warriors.Warrior;
import model.conditions.BeenAttacked;
import model.conditions.BeenDisarmed;
import model.conditions.CanCounterAttack;
import model.gamestate.Attack;
import model.gamestate.GameState;

public class CounterAttack extends Trigger {
    {
        conditions.put(new BeenAttacked(), true);
        conditions.put(new BeenDisarmed(), false);
        conditions.put(new CanCounterAttack(), true);
    }

    public CounterAttack(Warrior warrior) {
        super(warrior);
    }

    @Override
    void apply(GameState gameState) {
        Attack attack=(Attack)gameState;
        getWarrior().getCell().getGame().attack(attack.getAttecked().getCell(),
                attack.getAttacker().getCell());
    }
}
