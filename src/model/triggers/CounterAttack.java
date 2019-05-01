package model.triggers;

import model.Collection;
import model.QualityHaver;
import model.cards.warriors.Warrior;
import model.conditions.HasBeenAttacked;
import model.effects.Attacked;
import model.effects.Dispelablity;
import model.gamestate.AttackState;
import model.gamestate.GameState;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

//special because it's so common. also the action attack doesn't implement TriggerAction.
//this triggers should be added to all minions, with -1 duration.
public class CounterAttack extends Trigger {
    {
        conditions.add(new HasBeenAttacked());
    }

    public CounterAttack(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        AttackState attackState =(AttackState)gameState;
        Warrior warrior = (Warrior)owner;
        warrior.getCell().getBoard().getGame().attack(attackState.getAttacked().getCell(),
                attackState.getAttacker().getCell());
        warrior.getEffects().removeAll(warrior.getEffects().stream().filter(effect -> effect instanceof Attacked).collect(Collectors.toCollection(ArrayList::new)));
    }
}
