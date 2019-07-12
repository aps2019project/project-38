package model.triggers;

import model.QualityHaver;
import model.conditions.IsAttacking;
import model.conditions.IsMoving;
import model.effects.Dispelablity;
import model.gamestate.AttackState;
import model.gamestate.GameState;
import model.gamestate.MoveState;
//special because of unique action.
public class Stun extends Trigger {
    {
        conditions.add(new IsMoving().or(new IsAttacking()));
    }

    public Stun(int duration, Dispelablity dispelablity) {
        super(duration, dispelablity);
    }

    @Override
    protected void executeActions(GameState gameState, QualityHaver owner) {
        if(gameState instanceof AttackState){
            ((AttackState)gameState).canceled=true;
        }else if(gameState instanceof MoveState){
            ((MoveState)gameState).canceled=true;
        }
    }
}
