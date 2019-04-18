package model.triggers;

import model.Game;
import model.cards.warriors.Warrior;
import model.conditions.BeenAttacked;
import model.conditions.BeenDisarmed;
import model.conditions.CanCounterAttack;
import model.gamestate.Attack;
import model.gamestate.GameState;

public class CounterAttack extends Trigger {
    public CounterAttack(Warrior warrior) {
        super(warrior);
        conditions.put(new BeenAttacked(),true);
        conditions.put(new BeenDisarmed(),false);
        conditions.put(new CanCounterAttack(),true);
    }

    @Override
    void apply(GameState gameState) {
        getWarrior().getCell().getGame().attack(((Attack) gameState).getAttecked().getCell(),
                ((Attack) gameState).getAttacker().getCell());
    }
}
