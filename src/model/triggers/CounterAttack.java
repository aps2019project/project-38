package model.triggers;

import model.QualityHaver;
import model.actions.Attack;
import model.cards.Warrior;
import model.conditions.HasBeenAttacked;
import model.effects.Attacked;
import model.effects.Dispelablity;
import model.effects.Effect;
import model.gamestate.AttackState;
import model.gamestate.GameState;

import java.util.ArrayList;
import java.util.stream.Collectors;

//special because it's so common. also the action attack doesn't implement AutoAction.
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
        Attack.doIt(attackState.getAttacked().getCell(),
                attackState.getAttacker().getCell(),true);
    }
}
