package model.triggers;

import model.QualityHaver;
import model.cards.Warrior;
import model.conditions.IsBeingAttacked;
import model.effects.Dispelablity;
import model.gamestate.AttackState;
import model.gamestate.GameState;
//special because unique action and condition.
public class LowerAttackCanceller extends Trigger {
    {
        conditions.add(new IsBeingAttacked());
        //checks if the attacker ap is less than out ap.
        conditions.add((gameState, trigger, triggerOwner) ->
                ((AttackState)gameState).getAttacker().getAp()<((Warrior)triggerOwner).getAp());
    }
    public LowerAttackCanceller(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        ((AttackState)gameState).canceled=true;
    }
}
