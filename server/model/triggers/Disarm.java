package model.triggers;

import model.QualityHaver;
import model.conditions.IsAttacking;
import model.effects.Dispelablity;
import model.gamestate.AttackState;
import model.gamestate.GameState;
//special because of unique action. and has anti.
public class Disarm extends Trigger {
    {
        conditions.add(new IsAttacking());
    }
    public Disarm(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        ((AttackState)gameState).canceled=true;
    }
}
